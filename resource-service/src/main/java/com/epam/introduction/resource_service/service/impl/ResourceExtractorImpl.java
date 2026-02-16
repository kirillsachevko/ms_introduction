package com.epam.introduction.resource_service.service.impl;

import com.epam.introduction.resource_service.model.Mp3Entity;
import com.epam.introduction.resource_service.exception.MetadataProcessingException;
import com.epam.introduction.resource_service.dto.Mp3Metadata;
import com.epam.introduction.resource_service.service.ResourceExtractor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ResourceExtractorImpl implements ResourceExtractor {

    private final BodyContentHandler contentHandler;
    private final Mp3Parser mp3Parser;
    private final Mp3MetadataMapper metadataMapper;

    public ResourceExtractorImpl(BodyContentHandler contentHandler,
                                 Mp3Parser mp3Parser,
                                 Mp3MetadataMapper metadataMapper) {
        this.contentHandler = contentHandler;
        this.mp3Parser = mp3Parser;
        this.metadataMapper = metadataMapper;
    }

    @Override
    public Mp3Metadata extractMp3Metadata(Mp3Entity entity) {
        Metadata metadata = extractMetadata(entity);
        return parseMetadata(metadata, entity.getId());
    }

    private Metadata extractMetadata(Mp3Entity entity) {
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        try (InputStream inputStream = new ByteArrayInputStream(entity.getBinaryData())) {
            mp3Parser.parse(inputStream, contentHandler, metadata, context);
            return metadata;

        } catch (TikaException | SAXException | IOException e) {
            throw new MetadataProcessingException("File processing was failed");
        }
    }

    private Mp3Metadata parseMetadata(Metadata metadata, Long resourceId) {
        return metadataMapper.fromMetadata(metadata, resourceId);
    }
}
