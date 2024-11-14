package br.com.SistemaControleInsumos.dtos.user;

import br.com.SistemaControleInsumos.enuns.UserRole;

import java.util.Date;
import java.util.UUID;

public record ResponseUserDto(UUID id, Date createAt, String name, String email, String password, Integer age, UserRole role) {}
