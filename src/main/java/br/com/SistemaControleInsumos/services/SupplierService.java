package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.suppliers.RequestSupplierDto;
import br.com.SistemaControleInsumos.dtos.suppliers.ResponseSupplierDto;
import br.com.SistemaControleInsumos.entities.Supplier;
import br.com.SistemaControleInsumos.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    public ResponseSupplierDto createSupplier(RequestSupplierDto request) {
        Supplier supplier = new Supplier(
                request.name(),
                request.cnpj()
        );
        Supplier supplierSaved = this.supplierRepository.save(supplier);
        ResponseSupplierDto response = new ResponseSupplierDto(
                supplierSaved.getId(),
                supplierSaved.getName(),
                supplierSaved.getCnpj()
        );
        return response;
    }
}
