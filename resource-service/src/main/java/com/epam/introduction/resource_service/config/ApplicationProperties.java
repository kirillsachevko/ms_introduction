package com.epam.introduction.resource_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "song-service")
public record ApplicationProperties(String url) {
}
