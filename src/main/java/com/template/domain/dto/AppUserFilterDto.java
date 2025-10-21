package com.template.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para filtro de usuário")
public record AppUserFilterDto (
        @Schema(description = "Nome de usuário para filtro", example = "Geraldo")
        String username,

        @Schema(description = "Senha do usuário para filtro", example = "senha123")
        String password){
}
