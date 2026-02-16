package com.epam.introduction.resource_service.service.impl;

import com.epam.introduction.resource_service.model.Mp3Entity;
import com.epam.introduction.resource_service.exception.ResourceServiceException;
import com.epam.introduction.resource_service.exception.ResourceServiceParameterException;
import com.epam.introduction.resource_service.repository.Mp3FileRepository;
import com.epam.introduction.resource_service.service.ResourcePersistenceService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ResourcePersistenceServiceImpl implements ResourcePersistenceService {
    private final Mp3FileRepository mp3FileRepository;

    public ResourcePersistenceServiceImpl(Mp3FileRepository mp3FileRepository) {
        this.mp3FileRepository = mp3FileRepository;
    }

    @Override
    @Transactional
    public Mp3Entity saveResource(byte[] resource) {
        return mp3FileRepository.save(new Mp3Entity(resource));
    }

    @Override
    public byte[] getResourceFromStorage(Long id) {
        Mp3Entity entity = mp3FileRepository.findById(id)
                .orElseThrow(() -> new ResourceServiceException(String.format("Resource with ID=%s not found", id)));
        return entity.getBinaryData();
    }

    @Override
    @Transactional
    public List<Long> deleteFromDb(String ids) {
        List<Long> entityIds = processParameters(ids);
        List<Long> existedIds = mp3FileRepository.findExistingIdsByIdIn(entityIds);
        if (!existedIds.isEmpty()) {
            mp3FileRepository.deleteAllById(existedIds);
        }
        return existedIds;
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
            throw new ResourceServiceParameterException(String.format("Invalid ID format: '%s'. Only positive integers are allowed", id));
        }
    }
}
