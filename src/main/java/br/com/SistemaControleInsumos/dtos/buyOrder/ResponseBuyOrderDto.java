package br.com.SistemaControleInsumos.dtos.buyOrder;

import br.com.SistemaControleInsumos.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ResponseBuyOrderDto(Long id, UUID createdBy, Long supplierId, String status, LocalDate expectedDeliveryDate, LocalDate createdAt, List<Product> products) {
}
