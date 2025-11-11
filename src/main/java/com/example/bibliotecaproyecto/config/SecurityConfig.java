package com.example.bibliotecaproyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Bean para configurar la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // 1. SOLO permitimos el login, el registro y los archivos estáticos
                        .requestMatchers("/iniciologin", "/register").permitAll()
                        .requestMatchers("/css/**", "/img/**").permitAll()

                        // 2. TODO lo demás (incluyendo "/", "/catalogo", etc.) requerirá autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        // 3. Le decimos a Spring dónde está nuestra página de login
                        .loginPage("/iniciologin")
                        // ¡ESTA ES LA LÍNEA QUE ARREGLA EL PROBLEMA!
                        .usernameParameter("email") // Le dice a Spring que use el campo 'email'
                        // 4. Le decimos a Spring cuál es la URL que procesa el login (la del th:action="@{/login}" del form)
                        .loginProcessingUrl("/login")
                        // 5. A dónde redirigir si el login es exitoso
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        // A dónde redirigir después de cerrar sesión
                        .logoutSuccessUrl("/iniciologin?logout")
                        .permitAll()
                );

        return http.build();
    }
}