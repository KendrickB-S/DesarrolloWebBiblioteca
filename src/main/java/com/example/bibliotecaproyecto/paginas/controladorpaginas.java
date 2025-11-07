package com.example.bibliotecaproyecto.paginas;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class controladorpaginas {

    @GetMapping("/")  // Ruta raíz de la aplicación
    public String mostrarPrincipal() {
        return "principal"; // Thymeleaf buscará principal.html
    }

    @GetMapping("/iniciologin")
    public String mostrarLogin() {
        return "iniciologin"; // corresponde al archivo iniciologin.html
    }

    @GetMapping("/contactos")
    public String mostrarContactos(){
        return "contactos";
    }

    @GetMapping("/prestamos")
    public String mostrarPrestamos(){
        return "prestamos";
    }

    @GetMapping("/catalogo")
    public String mostrarCatalogo(){
        return "catalogo";
    }


}
