package com.epam.introduction.resource_service.model;

import com.drew.lang.annotations.NotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Mp3Entity {
    @Id
    @NotNull
    @Positive
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private byte[] binaryData;

    public Mp3Entity(byte[] binaryData) {
        this.binaryData = binaryData;
    }
}
