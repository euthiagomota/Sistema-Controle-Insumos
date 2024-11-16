package br.com.SistemaControleInsumos.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "buy_orders")
public class BuyOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "buy_order_products",
            joinColumns = @JoinColumn(name = "buy_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Products> products;

    @JoinColumn(name = "created_by", nullable = false)
    private UUID createdBy;

    @JoinColumn(name = "supplier_id", nullable = false)
    private Long supplierId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(nullable = false, length = 50)
    private String status;

    @Column(nullable = false)
    private LocalDate expectedDeliveryDate;
}
