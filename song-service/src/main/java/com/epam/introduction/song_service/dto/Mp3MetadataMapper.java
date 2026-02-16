package com.epam.introduction.song_service.dto;

import com.epam.introduction.song_service.model.Mp3Metadata;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
public interface Mp3MetadataMapper {

    Mp3Metadata toEntity(Mp3MetadataDto mp3MetadataDto);

    Mp3MetadataDto toDto(Mp3Metadata mp3Metadata);

    @ObjectFactory
    default Mp3Metadata newEntity(Mp3MetadataDto mp3MetadataDto) {
        return new Mp3Metadata(
                mp3MetadataDto.getId(),
                mp3MetadataDto.getName(),
                mp3MetadataDto.getArtist(),
                mp3MetadataDto.getAlbum(),
                mp3MetadataDto.getDuration(),
                mp3MetadataDto.getYear()
        );
    }
}
