package br.com.SistemaControleInsumos.repositories;

import br.com.SistemaControleInsumos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    List<User> findByCreateAtBetween(Timestamp initialDate, Timestamp finishDate);

}
