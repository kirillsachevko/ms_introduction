package com.epam.introduction.song_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mp3Metadata implements Serializable {
    @Id
    private Long id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
