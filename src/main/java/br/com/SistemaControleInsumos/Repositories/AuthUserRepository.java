package br.com.SistemaControleInsumos.Repositories;

import br.com.SistemaControleInsumos.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthUserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}
