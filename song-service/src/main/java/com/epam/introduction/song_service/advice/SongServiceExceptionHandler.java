package com.epam.introduction.song_service.advice;

import com.epam.introduction.song_service.exception.SongNotExistsException;
import com.epam.introduction.song_service.exception.SongServiceException;
import com.epam.introduction.song_service.exception.SongServiceParameterException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class SongServiceExceptionHandler {
    private static final String VALIDATION_EXCEPTION_MESSAGE = "Validation error";
    private static final String PARAM_MISMATCH_EXCEPTION_MESSAGE = "Invalid value '%s' for ID. Must be a positive integer";

    @ExceptionHandler(SongServiceException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(SongServiceException ex) {
        return createResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(SongNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotExistException(SongNotExistsException ex) {
        return createResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleParameterValidationException(MethodArgumentTypeMismatchException ex) {
        return createResponse(HttpStatus.BAD_REQUEST, String.format(PARAM_MISMATCH_EXCEPTION_MESSAGE, ex.getValue()));
    }

    @ExceptionHandler(SongServiceParameterException.class)
    public ResponseEntity<ErrorResponse> handleResourceIdValidation(SongServiceParameterException ex) {
        return createResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        VALIDATION_EXCEPTION_MESSAGE,
                        getInvalidPropertyNameMap(ex.getConstraintViolations().stream().toList())));
    }

    private HashMap<String, String> getInvalidPropertyNameMap(List<ConstraintViolation<?>> violations) {
        HashMap<String, String> detailsMap = new HashMap<>();
        violations.forEach(violation -> {
            String propertyName = extractLeafPropertyName(violation.getPropertyPath());
            if (Objects.nonNull(propertyName)) {
                detailsMap.put(propertyName, violation.getMessage());
            }
        });
        return detailsMap;
    }

    private String extractLeafPropertyName(Path path) {
        Path.Node leaf = null;
        for (Path.Node node : path) {
            leaf = node;
        }
        return leaf != null ? leaf.getName() : null;
    }

    private ResponseEntity<ErrorResponse> createResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(String.valueOf(status.value()), message, null));
    }
}
