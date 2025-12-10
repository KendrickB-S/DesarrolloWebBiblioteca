package com.example.bibliotecaproyecto.controller;

import com.example.bibliotecaproyecto.entity.Usuario;
import com.example.bibliotecaproyecto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200") // 👈 habilita Angular
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        usuarios.forEach(u -> u.setContraseña(null)); // seguridad
        return usuarios;
    }

    @PostMapping
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody Usuario usuarioActualizado) {

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        u.setNombre(usuarioActualizado.getNombre());
        u.setEmail(usuarioActualizado.getEmail());
        u.setRol(usuarioActualizado.getRol());

        return usuarioRepository.save(u);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        usuarioRepository.deleteById(id);
    }
}
