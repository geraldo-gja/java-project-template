package com.template.controller.apidoc;

import com.template.domain.dto.AppUserFilterDto;
import com.template.domain.dto.AppUserRequestDto;
import com.template.domain.dto.AppUserResponseDto;

import com.template.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User")
public interface AppUserDoc {

    @GetMapping("/{id}")
    @Operation(summary = "Returns a user by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<AppUserResponseDto> findById(@PathVariable Long id);

    @GetMapping
    @Operation(summary = "List all users with optional paging and filters")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Users found",
                    content = @Content(schema = @Schema(implementation = Page.class))
            )
    })
    ResponseEntity<Page<AppUserResponseDto>> findAll(
            @ParameterObject AppUserFilterDto example,
            @ParameterObject Pageable pageable);

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created",
                    content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
            ),
    })
    ResponseEntity<AppUserResponseDto> create(@RequestBody @Valid AppUserRequestDto request);

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated",
                    content = @Content(schema = @Schema(implementation = AppUserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<AppUserResponseDto> update(
            @PathVariable Long id,
            @RequestBody AppUserRequestDto request);


    @DeleteMapping("/{id}")
    @Operation(summary = "Remove a user by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    ResponseEntity<Void> delete(@PathVariable Long id);

}
