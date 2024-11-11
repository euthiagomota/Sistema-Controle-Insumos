package br.com.SistemaControleInsumos.Dtos.User;

public record RequestUserDto(String name, String email, String password, String confirmpassword, Integer age) {
}
