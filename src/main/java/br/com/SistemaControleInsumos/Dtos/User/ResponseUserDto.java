package br.com.SistemaControleInsumos.Dtos.User;

import java.util.Date;
import java.util.UUID;

public record ResponseUserDto(UUID id, Date createAt, String name, String email, String password, Integer age) {}
