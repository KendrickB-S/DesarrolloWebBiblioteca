package com.example.bibliotecaproyecto.controller;

import com.example.bibliotecaproyecto.entity.Libro;
import com.example.bibliotecaproyecto.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import org.springframework.http.ResponseEntity;

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
}