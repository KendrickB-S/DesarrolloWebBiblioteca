package com.example.bibliotecaproyecto.repository;

import com.example.bibliotecaproyecto.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer> {
}