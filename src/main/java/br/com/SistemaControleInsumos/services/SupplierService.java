package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    //ResponseSupplierDto and RequestSupplierDto
    public void createSupplier() {

    }
}
