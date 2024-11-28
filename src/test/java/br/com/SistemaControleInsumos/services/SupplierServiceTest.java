package br.com.SistemaControleInsumos.services;

import br.com.SistemaControleInsumos.dtos.suppliers.RequestSupplierDto;
import br.com.SistemaControleInsumos.dtos.suppliers.ResponseSupplierDto;
import br.com.SistemaControleInsumos.entities.Supplier;
import br.com.SistemaControleInsumos.repositories.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SupplierServiceTest {

    @InjectMocks
    private SupplierService supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    private RequestSupplierDto requestDto;

    // Anotação que Inicia o mock antes de começar
    @BeforeEach
    void setUp() {
        // Inicializar o RequestSupplierDto com dados fictícios
        requestDto = new RequestSupplierDto("Fornecedor Teste", "12345678000195");
    }

    @Test
    void testCreateSupplier() {
        // Simular o comportamento do repositório
        Supplier supplier = new Supplier("Fornecedor Teste", "12345678000195");
        supplier.setId(1L);  // Atribuindo um ID fictício para o fornecedor

        Mockito.when(supplierRepository.save(Mockito.any(Supplier.class))).thenReturn(supplier);

        // Chamar o método que está sendo testado
        ResponseSupplierDto response = supplierService.createSupplier(requestDto);

        // Verificar se o retorno está correto
        assertEquals(1L, response.id());
        assertEquals("Fornecedor Teste", response.name());
        assertEquals("12345678000195", response.cnpj());
    }
}
