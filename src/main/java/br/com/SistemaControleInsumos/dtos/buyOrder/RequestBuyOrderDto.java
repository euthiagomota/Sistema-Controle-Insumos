package br.com.SistemaControleInsumos.dtos.buyOrder;

import br.com.SistemaControleInsumos.dtos.products.RequestProductBuyOrderDto;
import br.com.SistemaControleInsumos.entities.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RequestBuyOrderDto(List<RequestProductBuyOrderDto> products, UUID createdBy, Long supplierId, String status, LocalDate expectedDeliveryDate) {
}
