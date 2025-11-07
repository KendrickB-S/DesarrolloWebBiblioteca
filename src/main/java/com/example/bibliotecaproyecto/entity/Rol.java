package com.example.bibliotecaproyecto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRol;

    @Column(nullable = false, length = 50)
    private String nombre;
}