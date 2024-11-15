package br.com.SistemaControleInsumos.dtos.user;

import br.com.SistemaControleInsumos.enuns.UserRole;

public record UpdateUserDto(String name, String oldPassword, String password, String confirmPassword, Integer age, UserRole role) {
}
