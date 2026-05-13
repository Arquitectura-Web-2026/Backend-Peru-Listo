package com.upc.presulisto;

import com.upc.presulisto.entidades.Usuario;
import com.upc.presulisto.repositorio.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PresuListoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PresuListoApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedAdmin(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Crear administrador por defecto si no existe
            if (!usuarioRepository.existsByCorreo("admin@presulisto.com")) {
                Usuario admin = new Usuario();
                admin.setNombreCompleto("Administrador del Sistema");
                admin.setCorreo("admin@presulisto.com");
                admin.setPasswordHash(passwordEncoder.encode("Admin123456"));
                admin.setRole("ADMIN");
                usuarioRepository.save(admin);
                System.out.println("✅ Admin creado: admin@presulisto.com / Admin123456");
            } else {
                System.out.println("ℹ️ Admin ya existe, saltando seed");
            }
        };
    }
}
