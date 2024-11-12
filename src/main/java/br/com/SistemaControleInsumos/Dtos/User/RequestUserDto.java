package br.com.SistemaControleInsumos.Dtos.User;

import br.com.SistemaControleInsumos.Enuns.UserRole;

public record RequestUserDto(String name, String email, String password, String confirmPassword, Integer age, UserRole role) {}
