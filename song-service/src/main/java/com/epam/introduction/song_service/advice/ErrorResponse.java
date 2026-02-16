package com.epam.introduction.song_service.advice;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(String errorCode, String errorMessage, HashMap<String, String> details) {
}
