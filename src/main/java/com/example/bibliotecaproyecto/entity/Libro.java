package com.example.bibliotecaproyecto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libro")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLibro;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(name = "cantidad_disponible", nullable = false)
    private Integer cantidadDisponible = 0;

    // Guardará el nombre del archivo de imagen
    @Column(name = "portada_url", length = 255)
    private String portadaUrl;
}
