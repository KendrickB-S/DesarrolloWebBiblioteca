package com.example.bibliotecaproyecto.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "prestamo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPrestamo;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fechaPrestamo;

    @Column(nullable = false)
    private LocalDate fechaDevolucionEsperada;

    private LocalDate fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EstadoPrestamo estado;

    @OneToMany(mappedBy = "prestamo", cascade = CascadeType.ALL)
    private List<DetallePrestamo> detalles;
}