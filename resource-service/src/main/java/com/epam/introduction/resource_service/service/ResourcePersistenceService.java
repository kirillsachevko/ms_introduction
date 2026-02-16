package com.epam.introduction.resource_service.service;

import com.epam.introduction.resource_service.model.Mp3Entity;

import java.util.List;

public interface ResourcePersistenceService {
    Mp3Entity saveResource(byte[] resource);
    byte[] getResourceFromStorage(Long id);
    List<Long> deleteFromDb(String ids);
}
