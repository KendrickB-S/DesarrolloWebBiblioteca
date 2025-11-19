package com.example.bibliotecaproyecto.service;

import com.example.bibliotecaproyecto.entity.Rol;
import com.example.bibliotecaproyecto.entity.Usuario;
import com.example.bibliotecaproyecto.repository.RolRepository;
import com.example.bibliotecaproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    public Usuario registrarUsuario(String nombre, String email, String contrasena) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Buscar el rol por defecto (Ej: "USER").

        Rol rolUsuario = rolRepository.findById(2).orElseThrow(() -> new RuntimeException("No se encontró el rol de usuario"));

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        // 4. ¡IMPORTANTE! Encriptar la contraseña
        nuevoUsuario.setContraseña(passwordEncoder.encode(contrasena));
        nuevoUsuario.setRol(rolUsuario);

        // Guardar en la BD
        return usuarioRepository.save(nuevoUsuario);
    }
}