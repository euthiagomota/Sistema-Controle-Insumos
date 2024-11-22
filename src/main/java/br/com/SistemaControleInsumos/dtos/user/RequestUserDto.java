package br.com.SistemaControleInsumos.dtos.user;

import br.com.SistemaControleInsumos.enuns.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestUserDto(
        @NotBlank(message = "name is mandatory!")
        String name,

        @Email(message = "invalid email!")
        @NotBlank(message = "email is mandatory!")
        String email,

        @NotBlank(message = "password is mandatory!")
        String password,

        @NotBlank(message = "confirm password is mandatory!")
        String confirmPassword,
        @NotBlank(message = "age is mandatory!")
        Integer age,

        @NotBlank(message = "role is mandatory!")
        UserRole role) {}
