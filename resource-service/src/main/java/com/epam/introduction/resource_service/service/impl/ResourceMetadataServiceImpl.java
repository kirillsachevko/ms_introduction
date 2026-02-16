package com.epam.introduction.resource_service.service.impl;

import com.epam.introduction.resource_service.config.ApplicationProperties;
import com.epam.introduction.resource_service.dto.Mp3Metadata;
import com.epam.introduction.resource_service.service.ResourceMetadataService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class ResourceMetadataServiceImpl implements ResourceMetadataService {
    private static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON;

    private final ApplicationProperties properties;
    private final RestTemplate restTemplate;

    public ResourceMetadataServiceImpl(
            ApplicationProperties properties, RestTemplate restTemplate) {
        this.properties = properties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<String, Long> invokeMetadataPersistence(Mp3Metadata metadata) {
        ResponseEntity<Map<String, Long>> response = restTemplate.exchange(properties.url(),
                HttpMethod.POST,
                createRequestEntity(metadata),
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    @Override
    public Map<String, List<Long>> removeMetadataByIds(String ids) {
        ResponseEntity<Map<String, List<Long>>> response = restTemplate.exchange(createUri(ids),
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<>() {
                });
        return response.getBody();
    }

    private HttpEntity<Object> createRequestEntity(Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(CONTENT_TYPE);
        return new HttpEntity<>(object, headers);
    }

    private URI createUri(String parameter) {
        return UriComponentsBuilder
                .fromUriString(properties.url())
                .queryParam("id", parameter)
                .build()
                .toUri();
    }
}
