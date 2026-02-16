package com.epam.introduction.song_service.controller;

import com.epam.introduction.song_service.dto.Mp3MetadataDto;
import com.epam.introduction.song_service.service.MetadataProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongServiceController {
    private final MetadataProcessor metadataProcessor;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> uploadMetadata(@RequestBody Mp3MetadataDto metadata) {
        return ResponseEntity.ok().body(metadataProcessor.uploadMetadataToDb(metadata));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Mp3MetadataDto> downloadMetadata(@PathVariable Long id) {
        return ResponseEntity.ok().body(metadataProcessor.downloadMetadataFromDb(id));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, List<Long>>> removeMetadata(@RequestParam String id) {
        return ResponseEntity.ok().body(metadataProcessor.removeMetadataFromDb(id));
    }
}
