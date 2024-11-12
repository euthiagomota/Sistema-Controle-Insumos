package br.com.SistemaControleInsumos.Dtos.User;

import br.com.SistemaControleInsumos.Enuns.UserRole;

public record UpdateUserDto(String name, String email, String oldPassword, String password, String confirmPassword, Integer age, UserRole role) {
}
