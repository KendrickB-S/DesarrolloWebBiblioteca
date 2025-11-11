package com.example.bibliotecaproyecto.paginas;

import com.example.bibliotecaproyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bibliotecaproyecto.entity.Libro;
import com.example.bibliotecaproyecto.service.LibroService;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class controladorpaginas {

    // Inyectamos nuestro servicio de usuario
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LibroService libroService;

    @GetMapping("/")
    public String mostrarPrincipal() {
        return "principal";
    }

    @GetMapping("/iniciologin")
    public String mostrarLogin() {
        return "iniciologin";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(Model model){ // Añade "Model model"
        // 1. Obtenemos todos los libros de la base de datos
        List<Libro> listaDeLibros = libroService.obtenerTodosLosLibros();

        // 2. Los añadimos al "model" para que Thymeleaf los vea
        model.addAttribute("libros", listaDeLibros);

        // 3. Devolvemos el nombre de la plantilla
        return "catalogo";
    }

    // ¡NUEVO! Este método maneja el POST del formulario de registro
    @PostMapping("/register")
    public String manejarRegistro(
            @RequestParam("name") String nombre,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) { // Para enviar mensajes de error/éxito

        // Validación simple
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos los campos son obligatorios");
            return "redirect:/iniciologin";
        }

        if (email.isEmpty() || !email.toLowerCase().endsWith("@gmail.com")) {
            redirectAttributes.addFlashAttribute("error", "El correo debe ser una dirección @gmail.com válida.");
            return "redirect:/iniciologin";
        }

        if (!password.equals(confirmPassword)) {
            // Las contraseñas no coinciden
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
            return "redirect:/iniciologin"; // Vuelve a la página de login
        }

        try {
            // Intentamos registrar al usuario
            usuarioService.registrarUsuario(nombre, email, password);
            redirectAttributes.addFlashAttribute("exito", "¡Cuenta creada! Ya puedes iniciar sesión.");

        } catch (RuntimeException e) {
            // Si el servicio lanza un error (ej: email ya existe)
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/iniciologin"; // Vuelve a la página de login
    }


    @GetMapping("/contactos")
    public String mostrarContactos(){
        return "contactos";
    }

    @GetMapping("/prestamos")
    public String mostrarPrestamos(){
        return "prestamos";
    }

}