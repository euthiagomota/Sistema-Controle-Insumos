package br.com.SistemaControleInsumos.controllers;

import br.com.SistemaControleInsumos.dtos.products.RequestProductDto;
import br.com.SistemaControleInsumos.dtos.products.ResponseProductDto;
import br.com.SistemaControleInsumos.dtos.user.ResponseUserDto;
import br.com.SistemaControleInsumos.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@Tag(name = "products", description = "this route is to do the control of products.")
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(summary = "create product", description = "This route creates a product")
    @ApiResponse(responseCode = "201", description = "Success to create product")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/create")
    public ResponseEntity<ResponseProductDto> createProduct(RequestProductDto requestProductDto) {
        ResponseProductDto response = this.productService.createProduct(requestProductDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "find all product", description = "this route is to find all product")
    @ApiResponse(responseCode = "200", description = "Success to find all product")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/stock")
    public ResponseEntity<List<ResponseProductDto>> findAllProducts() {
        List<ResponseProductDto> products = this.productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @Operation(summary = "delete product", description = "This route is to delete product")
    @ApiResponse(responseCode = "200", description = "Success to delete product")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable long id) {
        Boolean isDeleted = this.productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }

    @Operation(summary = "find product by id", description = "This route is to find product by id")
    @ApiResponse(responseCode = "200", description = "Success to find product by id")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDto> findProductById(@PathVariable long id) {
        ResponseProductDto product = this.productService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @Operation(summary = "date filter product", description = "This route is to filter product by date")
    @ApiResponse(responseCode = "200", description = "Success to filter product by date")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @GetMapping("/date-filter")
    public ResponseEntity<List<ResponseProductDto>> dateFilter(
            @RequestParam("initialDate") String initialDateStr,
            @RequestParam("finishDate") String finishDateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            Timestamp initialDate = new Timestamp(sdf.parse(initialDateStr).getTime());
            Timestamp finishDate = new Timestamp(sdf.parse(finishDateStr).getTime());
            List<ResponseProductDto> products = this.productService.dateFilter(initialDate, finishDate);
            return ResponseEntity.status(HttpStatus.OK).body(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "update product", description = "This route is to update product")
    @ApiResponse(responseCode = "200", description = "Success to update product")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PatchMapping("/stock/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(
            @PathVariable Long id, @RequestBody RequestProductDto request) {
            ResponseProductDto response = this.productService.updateProduct(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
