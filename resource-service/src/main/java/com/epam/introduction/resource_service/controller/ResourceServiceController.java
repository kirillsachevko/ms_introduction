package com.epam.introduction.resource_service.controller;

import com.epam.introduction.resource_service.service.ResourceProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/resources")
public class ResourceServiceController {

    private final ResourceProcessor resourceProcessor;

    public ResourceServiceController(ResourceProcessor resourceProcessor) {
        this.resourceProcessor = resourceProcessor;
    }

    @PostMapping(consumes = "audio/mpeg")
    public ResponseEntity<Map<String, Long>> uploadResource(@RequestBody byte[] mp3Binary) {
        return ResponseEntity.ok()
                .body(resourceProcessor.uploadResource(mp3Binary));
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> downloadResource(@PathVariable Long id) {
        return ResponseEntity.ok().body(resourceProcessor.downloadResource(id));
    }

    @DeleteMapping
    public ResponseEntity<Map<String,List<Long>>> deleteResources(@RequestParam("id") String ids) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(resourceProcessor.removeResources(ids));
    }
}
