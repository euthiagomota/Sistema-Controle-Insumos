package br.com.SistemaControleInsumos.repositories;

import br.com.SistemaControleInsumos.entities.BuyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyOrderRepository extends JpaRepository<BuyOrder, Long> {
}
