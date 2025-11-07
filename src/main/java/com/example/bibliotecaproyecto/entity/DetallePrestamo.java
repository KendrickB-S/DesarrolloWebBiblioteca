package com.example.bibliotecaproyecto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_prestamo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;

    @ManyToOne
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(nullable = false)
    private Integer cantidad;
}
