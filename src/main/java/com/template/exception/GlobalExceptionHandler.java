package com.template.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;


@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleException(Throwable ex, WebRequest request) {
        log.error(ex);

        HttpStatus status = determineHttpStatus(ex);
        String message = ex.getMessage() != null ? ex.getMessage() : "Internal Error";
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse response = new ErrorResponse(status.value(), message, path);
        return new ResponseEntity<>(response, status);
    }


    private HttpStatus determineHttpStatus(Throwable ex) {
        return switch (ex) {
            case EntityNotFoundException entityNotFoundException -> HttpStatus.NOT_FOUND;
            case DataIntegrityViolationException dataIntegrityViolationException -> HttpStatus.CONFLICT;
            case MethodArgumentNotValidException methodArgumentNotValidException -> HttpStatus.BAD_REQUEST;
            case BadRequestException badRequestException -> HttpStatus.BAD_REQUEST;
            case AccessDeniedException accessDeniedException -> HttpStatus.FORBIDDEN;
            case EmptyResultDataAccessException emptyResultDataAccessException -> HttpStatus.OK;
            case null, default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

}
