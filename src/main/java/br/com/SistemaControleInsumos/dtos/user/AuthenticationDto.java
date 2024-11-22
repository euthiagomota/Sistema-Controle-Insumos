package br.com.SistemaControleInsumos.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
        @Email(message = "invalid email!")
        @NotBlank(message = "email is mandatory!")
        String email,
        @NotBlank(message = "password is mandatory!")
        String password) {}
