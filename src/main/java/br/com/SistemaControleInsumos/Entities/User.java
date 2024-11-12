package br.com.SistemaControleInsumos.Entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "users")
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    Integer age;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date createAt;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }

}
