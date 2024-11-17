package br.com.SistemaControleInsumos.controllers;

import br.com.SistemaControleInsumos.dtos.suppliers.RequestSupplierDto;
import br.com.SistemaControleInsumos.dtos.suppliers.ResponseSupplierDto;
import br.com.SistemaControleInsumos.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    @Autowired
    SupplierService supplierService;

    @PostMapping("/register")
    public ResponseEntity<ResponseSupplierDto> createSupplier(RequestSupplierDto request) {
        ResponseSupplierDto response = this.supplierService.createSupplier(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
