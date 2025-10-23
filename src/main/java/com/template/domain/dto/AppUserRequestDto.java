package com.template.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AppUserRequestDto(

        @NotBlank(message = "Username is required")
        String username,

        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "Password must be at least 12 characters long and contain an uppercase letter, " +
                          "a lowercase letter, a number, and a special character"
        )
        String password
) {

}
