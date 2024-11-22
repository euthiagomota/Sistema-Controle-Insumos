package br.com.SistemaControleInsumos.controllers;

import br.com.SistemaControleInsumos.dtos.suppliers.RequestSupplierDto;
import br.com.SistemaControleInsumos.dtos.suppliers.ResponseSupplierDto;
import br.com.SistemaControleInsumos.services.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suppliers")
@Tag(name = "suppliers", description = "this route is to register suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @Operation(summary = "register suppliers", description = "This route is to register suppliers")
    @ApiResponse(responseCode = "201", description = "Success to create suppliers")
    @ApiResponse(responseCode = "400", description = "invalid data")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("/register")
    public ResponseEntity<ResponseSupplierDto> createSupplier(RequestSupplierDto request) {
        ResponseSupplierDto response = this.supplierService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
