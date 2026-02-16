package com.epam.introduction.resource_service.service;

import com.epam.introduction.resource_service.dto.Mp3Metadata;

import java.util.List;
import java.util.Map;

public interface ResourceMetadataService {
    Map<String, Long> invokeMetadataPersistence(Mp3Metadata metadata);

    Map<String, List<Long>> removeMetadataByIds(String ids);
}
