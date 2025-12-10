package com.example.bibliotecaproyecto.service;

import com.example.bibliotecaproyecto.entity.Libro;
import com.example.bibliotecaproyecto.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerLibroPorId(Integer id) {
        return libroRepository.findById(id);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminarLibro(Integer id) {
        libroRepository.deleteById(id);
    }

}