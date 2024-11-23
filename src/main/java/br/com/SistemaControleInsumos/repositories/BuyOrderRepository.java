package br.com.SistemaControleInsumos.repositories;

import br.com.SistemaControleInsumos.entities.BuyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
    List<BuyOrder> findByCreatedAtBetween(Timestamp initialDate, Timestamp finishDate);
}
