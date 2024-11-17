package br.com.SistemaControleInsumos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "suppliers")
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 14)
    private String cnpj;

    public Supplier(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }
}
