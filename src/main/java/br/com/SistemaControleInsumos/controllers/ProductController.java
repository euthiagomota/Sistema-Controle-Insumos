package br.com.SistemaControleInsumos.controllers;

import br.com.SistemaControleInsumos.dtos.products.RequestProductDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.dtos.user.ResponseUserDto;
import br.com.SistemaControleInsumos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<ResponseProductDto> createProduct(RequestProductDto requestProductDto) {
        ResponseProductDto response = this.productService.createProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDto>> findAllProducts() {
        List<ResponseProductDto> products = this.productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable long id) {
        Boolean isDeleted = this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> findProductById(@PathVariable long id) {
        ResponseProductDto user = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
