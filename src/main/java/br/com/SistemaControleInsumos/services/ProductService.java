package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.products.RequestProductDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.entities.Product;
import br.com.SistemaControleInsumos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ResponseProductDto createProduct(RequestProductDto requestProductDto) {
        Product product = new Product(
                requestProductDto.name(),
                requestProductDto.description(),
                requestProductDto.quantity(),
                requestProductDto.supplierId(),
                requestProductDto.expirationDate()
        );
        Product productSaved = this.productRepository.save(product);

        ResponseProductDto response = new ResponseProductDto(
                productSaved.getName(),
                productSaved.getDescription(),
                productSaved.getQuantity(),
                productSaved.getSupplierId(),
                productSaved.getExpirationDate(),
                productSaved.getCreatedAt(),
                productSaved.getUpdatedAt(),
                productSaved.getActive()
        );
        return response;
    }
}
