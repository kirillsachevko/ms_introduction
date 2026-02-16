package com.epam.introduction.resource_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mp3Metadata {
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
