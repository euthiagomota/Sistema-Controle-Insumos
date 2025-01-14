package br.com.SistemaControleInsumos.dtos.products;

import java.time.LocalDate;

public record ResponseProductDto(
        Long id,
        String name,
        String description,
        Integer quantity,
        Long supplierId,
        LocalDate expirationDate,
        LocalDate createdAt,
        LocalDate updatedAt,
        Boolean active

) {
}
