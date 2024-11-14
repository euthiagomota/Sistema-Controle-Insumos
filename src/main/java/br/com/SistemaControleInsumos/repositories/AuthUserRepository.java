package br.com.SistemaControleInsumos.repositories;

import br.com.SistemaControleInsumos.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
