package com.epam.introduction.resource_service.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;
import java.util.Map;

public interface ResourceProcessor {
    Map<String, Long> uploadResource(byte[] resource);

    byte[] downloadResource(@Valid @Positive(message = "Invalid value '${validatedValue}' for ID. Must be a positive integer") Long id);

    Map<String, List<Long>> removeResources(String ids);

}
