package br.com.SistemaControleInsumos.dtos.user;

import br.com.SistemaControleInsumos.enuns.UserRole;

public record RequestUserDto(String name, String email, String password, String confirmPassword, Integer age, UserRole role) {}
