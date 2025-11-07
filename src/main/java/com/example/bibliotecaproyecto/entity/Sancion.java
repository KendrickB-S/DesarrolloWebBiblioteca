package com.example.bibliotecaproyecto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "sancion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSancion;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String motivo;  // Ejemplo: "Retraso en devolución del libro"

    @Column(nullable = false)
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean activa = true;
}
