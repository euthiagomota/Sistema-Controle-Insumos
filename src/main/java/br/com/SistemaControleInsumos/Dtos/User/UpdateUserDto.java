package br.com.SistemaControleInsumos.Dtos.User;

public record UpdateUserDto(String name, String email, String oldPassword, String password, String confirmPassword, Integer age) {
}
