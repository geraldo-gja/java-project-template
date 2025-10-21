package com.template.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Standard API error response")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Error message", example = "User not found")
        String message,

        @Schema(description = "Error timestamp", example = "2025-01-15T10:30:00")
        LocalDateTime timestamp,

        @Schema(description = "Request path", example = "/api/users/123")
        String path
) {
    public ErrorResponse(int status, String message, String path) {
        this(status, message, LocalDateTime.now(), path);
    }
}
