package com.epam.introduction.resource_service.service;

import com.epam.introduction.resource_service.model.Mp3Entity;
import com.epam.introduction.resource_service.dto.Mp3Metadata;

public interface ResourceExtractor {
    Mp3Metadata extractMp3Metadata(Mp3Entity entity);
}
