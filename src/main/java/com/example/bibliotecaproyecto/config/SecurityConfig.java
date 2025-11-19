package com.example.bibliotecaproyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Importación necesaria para .cors(withDefaults())
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(csrf -> csrf.disable()) // Desactivado para facilitar pruebas de API
                .authorizeHttpRequests(auth -> auth
                        // 1. REGLAS ESPECÍFICAS (Las excepciones van PRIMERO)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll() // Permitir la API para Angular
                        .requestMatchers("/iniciologin", "/register").permitAll() // Login y registro
                        .requestMatchers("/css/**", "/img/**").permitAll() // Recursos estáticos

                        // 2. REGLA GENERAL (El "catch-all" va AL FINAL)
                        .anyRequest().authenticated() // Todo lo demás requiere login
                )
                .formLogin(form -> form
                        .loginPage("/iniciologin")
                        .usernameParameter("email")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/iniciologin?logout")
                        .permitAll()
                );

        return http.build();
    }
}