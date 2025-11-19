package com.example.bibliotecaproyecto.controller;

import com.example.bibliotecaproyecto.entity.Usuario;
import com.example.bibliotecaproyecto.service.UsuarioService;
import com.example.bibliotecaproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // DTO para recibir datos de Login
    public record LoginRequest(String email, String password) {}

    // DTO para recibir datos de Registro
    public record RegisterRequest(String nombre, String email, String password) {}

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(request.nombre, request.email, request.password);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado exitosamente", "usuario", usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // Buscar usuario por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Verificar la contraseña
            if (passwordEncoder.matches(request.password, usuario.getContraseña())) {
                // Devolvemos el usuario (SIN la contraseña por seguridad)
                usuario.setContraseña(null);
                return ResponseEntity.ok(usuario);
            }
        }

        return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
    }
}