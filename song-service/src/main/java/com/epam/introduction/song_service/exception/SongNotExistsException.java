package com.epam.introduction.song_service.exception;

public class SongNotExistsException extends RuntimeException {
    public SongNotExistsException(String message) {
        super(message);
    }
}
