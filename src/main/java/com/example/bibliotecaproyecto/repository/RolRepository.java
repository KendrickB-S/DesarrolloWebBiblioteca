package com.example.bibliotecaproyecto.repository;

import com.example.bibliotecaproyecto.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}