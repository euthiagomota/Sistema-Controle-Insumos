package br.com.SistemaControleInsumos.exceptions.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ExceptionResponse(
        LocalDateTime timestamp,
        Integer status,
        String error,
        String message,
        String path) {
}
