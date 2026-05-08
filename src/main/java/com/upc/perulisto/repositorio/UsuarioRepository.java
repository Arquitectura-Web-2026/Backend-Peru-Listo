package com.upc.perulisto.repositorio;

import com.upc.perulisto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    Optional<Usuario> findByResetToken(String resetToken);
}
