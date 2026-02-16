package com.epam.introduction.song_service.dto;

import com.epam.introduction.song_service.util.AdvancedChecks;
import com.epam.introduction.song_service.util.BasicChecks;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
@GroupSequence({BasicChecks.class, AdvancedChecks.class, Mp3MetadataDto.class})
public class Mp3MetadataDto implements Serializable {

    @Positive(groups = BasicChecks.class, message = "Invalid value '${validatedValue}' for ID. Must be a positive integer")
    private long id;

    @NotNull(groups = BasicChecks.class, message = "Song name is required")
    @Size(groups = AdvancedChecks.class, min = 1, max = 100, message = "Song name must be between 1 and 100 characters")
    private String name;

    @NotNull(groups = BasicChecks.class, message = "Artist name is required")
    @Size(groups = AdvancedChecks.class, min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String artist;

    @NotNull(groups = BasicChecks.class, message = "Album name is required")
    @Size(groups = AdvancedChecks.class, min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
    private String album;

    @NotNull(groups = BasicChecks.class, message = "Duration is required")
    @Pattern(groups = AdvancedChecks.class, regexp = "^[0-5]\\d:[0-5]\\d$", message = "Duration must be in mm:ss format with leading zeros")
    private String duration;

    @NotNull(groups = BasicChecks.class, message = "Year is required")
    @Pattern(groups = AdvancedChecks.class, regexp = "^(19\\d{2}|20\\d{2})$", message = "Year must be between 1900 and 2099")
    private String year;
}
