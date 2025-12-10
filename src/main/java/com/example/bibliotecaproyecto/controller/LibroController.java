package com.example.bibliotecaproyecto.controller;

import com.example.bibliotecaproyecto.entity.Libro;
import com.example.bibliotecaproyecto.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import java.util.List;

@RestController // <--- ¡Esto es clave! Dice que devuelve DATOS, no vistas HTML
@RequestMapping("/api/libros") // La ruta base será http://localhost:8080/api/libros
public class LibroController {

    @Autowired
    private LibroService libroService;

    // Endpoint para obtener todos los libros
    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodosLosLibros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> obtenerPorId(@PathVariable Integer id) {
        Optional<Libro> libro = libroService.obtenerLibroPorId(id);

        // Si lo encuentra, lo devuelve con status 200 OK. Si no, devuelve 404 Not Found.
        return libro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // NUEVO: Endpoint para registrar un nuevo libro (CREATE)
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        // Asumiendo que el campo 'cantidadDisponible' se usa para el stock total
        if (libro.getCantidadDisponible() == null) {
            libro.setCantidadDisponible(0);
        }
        Libro nuevoLibro = libroService.guardarLibro(libro);
        return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
    }

    // NUEVO: Endpoint para actualizar un libro existente (UPDATE)
    @PutMapping("/{id}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable Integer id, @RequestBody Libro libroDetalles) {
        return libroService.obtenerLibroPorId(id)
                .map(libroExistente -> {
                    // Actualizar los campos del libro existente
                    libroExistente.setTitulo(libroDetalles.getTitulo());
                    libroExistente.setAutor(libroDetalles.getAutor());
                    libroExistente.setCategoria(libroDetalles.getCategoria());
                    libroExistente.setCantidadTotal(libroDetalles.getCantidadTotal());
                    libroExistente.setCantidadDisponible(libroDetalles.getCantidadDisponible());
                    libroExistente.setPortadaUrl(libroDetalles.getPortadaUrl());

                    Libro actualizado = libroService.guardarLibro(libroExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // NUEVO: Endpoint para eliminar un libro (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable Integer id) {
        try {
            libroService.eliminarLibro(id);
            return ResponseEntity.noContent().build(); // Status 204 No Content
        } catch (Exception e) {
            // Si hay un error de clave foránea (el libro está prestado), devuelve 409
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}