package com.template.controller.apidoc;

import com.template.domain.dto.AppUserFilterDto;
import com.template.domain.dto.AppUserRequestDto;
import com.template.domain.dto.AppUserResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Usuário")
public interface AppUserDoc {

    @GetMapping("/{id}")
    @Operation(summary = "Devolve um usuário pelo ID")
    ResponseEntity<AppUserResponseDto> findById(@PathVariable Long id);

    @GetMapping
    @Operation(summary = "Lista todos os usuários com paginação e filtros opcionais")
    ResponseEntity<Page<AppUserResponseDto>> findAll(
            @ParameterObject AppUserFilterDto example,
            @ParameterObject Pageable pageable);

    @PostMapping
    @Operation(summary = "Cria um novo usuário")
    ResponseEntity<AppUserResponseDto> create(@RequestBody AppUserRequestDto request);

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário existente")
    ResponseEntity<AppUserResponseDto> update(
            @PathVariable Long id,
            @RequestBody AppUserRequestDto request);

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário por ID")
    ResponseEntity<Void> delete(@PathVariable Long id);

}
