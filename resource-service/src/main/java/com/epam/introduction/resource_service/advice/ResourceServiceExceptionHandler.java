package com.epam.introduction.resource_service.advice;

import com.epam.introduction.resource_service.exception.MetadataProcessingException;
import com.epam.introduction.resource_service.exception.ResourceServiceException;
import com.epam.introduction.resource_service.exception.ResourceServiceParameterException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ResourceServiceExceptionHandler {

    private static final String BAD_REQUEST_CODE = "400";
    private static final String NOT_FOUND_CODE = "404";
    private static final String INTERNAL_ERROR_CODE = "500";

    @ExceptionHandler(ResourceServiceException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundError(ResourceServiceException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(NOT_FOUND_CODE, ex.getMessage()));
    }

    @ExceptionHandler(MetadataProcessingException.class)
    public ResponseEntity<ErrorResponse> handleServerError(MetadataProcessingException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(INTERNAL_ERROR_CODE, ex.getMessage()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleUnsupportedType(HttpMediaTypeNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(BAD_REQUEST_CODE, String.format("Invalid file format: %s. Only MP3 files are allowed", ex.getContentType())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleParameterTypeError(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(BAD_REQUEST_CODE, String.format("Invalid value '%s' for ID. Must be a positive integer", ex.getValue())));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(String.join(", ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(BAD_REQUEST_CODE, message));
    }

    @ExceptionHandler(ResourceServiceParameterException.class)
    public ResponseEntity<ErrorResponse> handleParameterException(ResourceServiceParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(BAD_REQUEST_CODE, ex.getMessage()));
    }

    public record ErrorResponse(String errorCode, String errorMessage) {
    }
}
