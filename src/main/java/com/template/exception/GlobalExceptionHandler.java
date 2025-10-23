package com.template.exception;

import lombok.extern.log4j.Log4j2;

import jakarta.persistence.EntityNotFoundException;

import java.nio.file.AccessDeniedException;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${logging.colors.enabled:false}")
    private boolean colorsEnabled;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(Throwable ex, WebRequest request) {
        String message = ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred";
        return buildErrorMessage(ex, request, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .filter(m -> m != null && !m.isBlank())
                .findFirst()
                .orElse("Validation failed");

        return buildErrorMessage(ex, request, message);
    }

    private ResponseEntity<ErrorResponse> buildErrorMessage(Throwable ex, WebRequest request, String message) {
        printErrorLog(ex);

        HttpStatus status = determineHttpStatus(ex);
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse response = new ErrorResponse(status.value(), message, path);
        return new ResponseEntity<>(response, status);
    }

    private HttpStatus determineHttpStatus(Throwable ex) {
        return switch (ex) {
            case EntityNotFoundException ignored -> HttpStatus.NOT_FOUND;
            case DataIntegrityViolationException ignored -> HttpStatus.CONFLICT;
            case MethodArgumentNotValidException ignored -> HttpStatus.BAD_REQUEST;
            case BadRequestException ignored -> HttpStatus.BAD_REQUEST;
            case AccessDeniedException ignored -> HttpStatus.FORBIDDEN;
            case EmptyResultDataAccessException ignored -> HttpStatus.OK;
            case null, default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    private void printErrorLog(Throwable ex) {
        if (colorsEnabled) {
            String RED = "\u001B[31m";
            String RESET = "\u001B[0m";
            log.error("{}Exception: {}{}", RED, ex.getMessage(), RESET);
        }else
            log.error("Exception: {}", ex.getMessage(), ex);
    }

}
