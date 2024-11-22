package br.com.SistemaControleInsumos.dtos.products;

import jakarta.persistence.Column;

import java.time.LocalDate;

public record RequestProductDto(
        String name,
        String description,
        Integer quantity,
        Long supplierId,
        LocalDate expirationDate,
        Boolean active
) {
}
