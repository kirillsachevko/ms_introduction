package com.epam.introduction.song_service.service.impl;

import com.epam.introduction.song_service.dto.Mp3MetadataDto;
import com.epam.introduction.song_service.dto.Mp3MetadataMapper;
import com.epam.introduction.song_service.exception.SongNotExistsException;
import com.epam.introduction.song_service.exception.SongServiceException;
import com.epam.introduction.song_service.exception.SongServiceParameterException;
import com.epam.introduction.song_service.model.Mp3Metadata;
import com.epam.introduction.song_service.repository.Mp3MetadataRepository;
import com.epam.introduction.song_service.service.MetadataProcessor;
import com.epam.introduction.song_service.util.ResponseProperty;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Validated
public class MetadataProcessorImpl implements MetadataProcessor {

    private static final int IDS_SIZE_LIMIT = 200;

    private final Mp3MetadataRepository metadataRepository;
    private final Mp3MetadataMapper metadataMapper;

    @Override
    @Transactional
    public Map<String, Long> uploadMetadataToDb(Mp3MetadataDto metadataDto) {
        Mp3Metadata metadata = metadataMapper.toEntity(metadataDto);

        checkExistence(metadata.getId());

        Long id = metadataRepository.saveAndFlush(metadata).getId();
        return Map.of(ResponseProperty.ID.getValue(), id);
    }

    @Override
    public Mp3MetadataDto downloadMetadataFromDb(Long id) {
        if (id < 1) {
            throw new SongServiceParameterException(String.format("Invalid value '%s' for ID. Must be a positive integer", id));
        }
        Mp3Metadata metadata = metadataRepository.findById(id)
                .orElseThrow(() -> new SongNotExistsException(String.format("Song metadata for ID=%s not found", id)));
        return metadataMapper.toDto(metadata);
    }

    @Override
    @Transactional
    public Map<String, List<Long>> removeMetadataFromDb(String ids) {
        validateParameterSize(ids);
        List<Long> resourceIds = processParameters(ids);
        List<Long> existedIds = metadataRepository.findExistingIdsByIdIn(resourceIds);
        if (!existedIds.isEmpty()) {
            metadataRepository.deleteAllById(existedIds);
            return Map.of(ResponseProperty.IDS.getValue(), existedIds);
        }
        return Map.of(ResponseProperty.IDS.getValue(), Collections.emptyList());
    }

    private void checkExistence(Long id) {
        if (metadataRepository.existsById(id)) {
            throw new SongServiceException(String.format("Metadata for resource ID=%s already exists", id));
        }
    }

    private List<Long> processParameters(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(this::validateParameter)
                .toList();
    }

    private Long validateParameter(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new SongServiceParameterException(String.format("Invalid ID format: '%s'. Only positive integers are allowed", id));
        }
    }

    private void validateParameterSize(String ids) {
        int idsSize = ids.length();
        if (idsSize > IDS_SIZE_LIMIT) {
            throw new SongServiceParameterException(
                    String.format("CSV string is too long: received %d characters, maximum allowed is 200",
                            idsSize));
        }
    }
}
