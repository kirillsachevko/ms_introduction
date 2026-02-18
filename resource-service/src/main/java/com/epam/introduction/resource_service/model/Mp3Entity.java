package com.epam.introduction.resource_service.model;

import com.drew.lang.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mp3entity")
@Getter
@NoArgsConstructor
public class Mp3Entity {
    @Id
    @NotNull
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "binary_data")
    private byte[] binaryData;

    public Mp3Entity(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
