package com.example.bibliotecaproyecto.repository;

import com.example.bibliotecaproyecto.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Importar

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Spring Data JPA crea la consulta automáticamente
    Optional<Usuario> findByEmail(String email);
}

