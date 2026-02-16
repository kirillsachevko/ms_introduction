package com.epam.introduction.resource_service.service.impl;

import com.epam.introduction.resource_service.dto.Mp3Metadata;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class Mp3MetadataMapper {
    public Mp3Metadata fromMetadata(Metadata metadata, Long id) {
        return Mp3Metadata.builder()
                .id(id)
                .name(metadata.get("dc:title"))
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .duration(processDuration(metadata.get("xmpDM:duration")))
                .year(metadata.get("xmpDM:releaseDate"))
                .build();
    }

    private String processDuration(String millis) {
        long wholeDurationSeconds = Duration.ofMillis((long) Double.parseDouble(millis)).getSeconds();
        long minutes = wholeDurationSeconds / 60;
        long seconds = wholeDurationSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
