package br.com.SistemaControleInsumos.repositories;

import br.com.SistemaControleInsumos.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(long id);

    List<Product> findByCreatedAtBetween(Timestamp initialDate, Timestamp finishDate);
}
