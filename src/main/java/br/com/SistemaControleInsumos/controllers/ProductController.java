package br.com.SistemaControleInsumos.controllers;

import br.com.SistemaControleInsumos.dtos.products.RequestProductDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
