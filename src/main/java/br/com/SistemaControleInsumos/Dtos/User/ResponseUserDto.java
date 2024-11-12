package br.com.SistemaControleInsumos.Dtos.User;

import br.com.SistemaControleInsumos.Enuns.UserRole;

import java.util.Date;
import java.util.UUID;

public record ResponseUserDto(UUID id, Date createAt, String name, String email, String password, Integer age, UserRole role) {}
