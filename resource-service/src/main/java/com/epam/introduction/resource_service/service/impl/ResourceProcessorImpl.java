package com.epam.introduction.resource_service.service.impl;

import com.epam.introduction.resource_service.dto.Mp3Metadata;
import com.epam.introduction.resource_service.exception.ResourceServiceParameterException;
import com.epam.introduction.resource_service.model.Mp3Entity;
import com.epam.introduction.resource_service.service.ResourceExtractor;
import com.epam.introduction.resource_service.service.ResourceMetadataService;
import com.epam.introduction.resource_service.service.ResourcePersistenceService;
import com.epam.introduction.resource_service.service.ResourceProcessor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Service
@Validated
public class ResourceProcessorImpl implements ResourceProcessor {
    private static final int IDS_SIZE_LIMIT = 200;

    private final ResourcePersistenceService persistenceService;
    private final ResourceExtractor resourceExtractor;
    private final ResourceMetadataService metadataService;

    public ResourceProcessorImpl(ResourcePersistenceService persistenceService,
                                 ResourceExtractor resourceExtractor,
                                 ResourceMetadataService metadataService) {
        this.persistenceService = persistenceService;
        this.resourceExtractor = resourceExtractor;
        this.metadataService = metadataService;
    }

    @Override
    public Map<String, Long> uploadResource(byte[] resource) {
        Mp3Entity entity = persistenceService.saveResource(resource);
        Mp3Metadata metadata = resourceExtractor.extractMp3Metadata(entity);
        return metadataService.invokeMetadataPersistence(metadata);
    }

    @Override
    public byte[] downloadResource(Long id) {
        return persistenceService.getResourceFromStorage(id);
    }

    @Override
    public Map<String, List<Long>> removeResources(String ids) {
        validateParameterSize(ids);
        persistenceService.deleteFromDb(ids);
        return metadataService.removeMetadataByIds(ids);

    }

    private void validateParameterSize(String ids) {
        int idsSize = ids.length();
        if (idsSize > IDS_SIZE_LIMIT) {
            throw new ResourceServiceParameterException(
                    String.format("CSV string is too long: received %d characters, maximum allowed is 200",
                            idsSize));
        }
    }
}
