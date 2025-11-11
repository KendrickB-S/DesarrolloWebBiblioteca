package com.example.bibliotecaproyecto.service;

import com.example.bibliotecaproyecto.entity.Libro;
import com.example.bibliotecaproyecto.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }
}