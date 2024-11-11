package br.com.SistemaControleInsumos.UserDTOs;

public record RequestUserDto(String name, String email, String password, String confirmpassword, Integer age) {
}
