package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.products.RequestProductDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.entities.Product;
import br.com.SistemaControleInsumos.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                productSaved.getId(),
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

    public List<ResponseProductDto> findAll() {
        try {
            List<Product> products = this.productRepository.findAll();

            List<ResponseProductDto> responseDtos = new ArrayList<>();
            for (Product product : products) {
                ResponseProductDto responseProductDto = new ResponseProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getSupplierId(),
                        product.getExpirationDate(),
                        product.getCreatedAt(),
                        product.getUpdatedAt(),
                        product.getActive()
                );
                responseDtos.add(responseProductDto);
            }
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the users", e);
        }

    }

    public Boolean delete(long id) {
        try {
            Optional<Product> optionalProduct = this.productRepository.findById(id);
            if (optionalProduct.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }
            this.productRepository.delete(optionalProduct.get());
            return true;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while deleting the user", e);
        }
    }

    public ResponseProductDto findById(long id) {
        try {
            Optional<Product> productOptional = this.productRepository.findById(id);
            if (productOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product not found!");
            }
            Product product = productOptional.get();

            ResponseProductDto response = new ResponseProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getQuantity(),
                    product.getSupplierId(),
                    product.getExpirationDate(),
                    product.getCreatedAt(),
                    product.getUpdatedAt(),
                    product.getActive()
            );


            return response;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the product", e);
        }

    }

    public List<ResponseProductDto> dateFilter(Timestamp initialDate, Timestamp finishDate) {
        try {
            List<Product> products = this.productRepository.findByCreatedAtBetween(
                    initialDate,
                    finishDate
            );
            List<ResponseProductDto> responseDtos = new ArrayList<>();

            for (Product product : products) {
                ResponseProductDto responseProductDto = new ResponseProductDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getSupplierId(),
                        product.getExpirationDate(),
                        product.getCreatedAt(),
                        product.getUpdatedAt(),
                        product.getActive()
                );
                responseDtos.add(responseProductDto);
            }
            return responseDtos;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "An error occurred while finding the user by date", e);
        }
    }

    public ResponseProductDto updateProduct(Long id, RequestProductDto request) {

        Optional<Product> optionalProduct = this.productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }

        Product productToSave = optionalProduct.get();

        if (request.name() != null && !request.name().isBlank()) {
            productToSave.setName(request.name());
        }
        if (request.description() != null && !request.description().isBlank()) {
            productToSave.setDescription(request.description());
        }
        if (request.active() != null) {
            productToSave.setActive(request.active());
        }
        if (request.expirationDate() != null) {
            productToSave.setExpirationDate(request.expirationDate());
        }
        if (request.supplierId() != null) {
            productToSave.setSupplierId(request.supplierId());
        }

        productToSave.setUpdatedAt(LocalDate.now());

        Product updatedProduct = this.productRepository.save(productToSave);

        return new ResponseProductDto(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getDescription(),
                updatedProduct.getQuantity(),
                updatedProduct.getSupplierId(),
                updatedProduct.getExpirationDate(),
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedAt(),
                updatedProduct.getActive()
        );
    }

}
