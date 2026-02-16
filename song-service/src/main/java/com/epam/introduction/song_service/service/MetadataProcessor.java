package com.epam.introduction.song_service.service;

import com.epam.introduction.song_service.dto.Mp3MetadataDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface MetadataProcessor {
    Map<String, Long> uploadMetadataToDb(@Valid Mp3MetadataDto metadataDto);

    Mp3MetadataDto downloadMetadataFromDb(Long id);

    Map<String, List<Long>> removeMetadataFromDb(String ids);
}
