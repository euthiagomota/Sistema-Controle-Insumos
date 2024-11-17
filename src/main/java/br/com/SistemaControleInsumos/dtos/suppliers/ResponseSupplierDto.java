package br.com.SistemaControleInsumos.dtos.suppliers;

public record ResponseSupplierDto(
        Long id,
        String name,
        String cnpj
) {
}
