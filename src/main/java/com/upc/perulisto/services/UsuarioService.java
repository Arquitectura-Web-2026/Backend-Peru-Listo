package com.upc.perulisto.services;


import com.upc.perulisto.DTO.PasswordChangeRequest;
import com.upc.perulisto.DTO.PasswordResetRequest;
import com.upc.perulisto.DTO.UsuarioDTO;
import com.upc.perulisto.entidades.Usuario;
import com.upc.perulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UsuarioDTO obtenerPerfil(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO actualizarPerfil(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.getCorreo().equals(usuarioDTO.getCorreo()) &&
                usuarioRepository.existsByCorreo(usuarioDTO.getCorreo())) {
            throw new RuntimeException("El correo ya está en uso");
        }

        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setCorreo(usuarioDTO.getCorreo());

        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public ResponseEntity<?> cambiarPassword(Long id, PasswordChangeRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getCurrentPassword(), usuario.getPasswordHash())) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("Error: La contraseña actual es incorrecta"));
        }

        if (request.getNewPassword().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("Error: La nueva contraseña debe tener al menos 8 caracteres"));
        }

        if (passwordEncoder.matches(request.getNewPassword(), usuario.getPasswordHash())) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("Error: La nueva contraseña debe ser diferente a la actual"));
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("Error: Las contraseñas no coinciden"));
        }

        usuario.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new com.upc.perulisto.DTO.MessageResponse("Contraseña actualizada exitosamente"));
    }

    public ResponseEntity<?> solicitarRecuperacion(String correo) {
        var usuarioOpt = usuarioRepository.findByCorreo(correo);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String token = UUID.randomUUID().toString();
            usuario.setResetToken(token);
            usuario.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);
            
            // Simular envío de correo (System.out.println)
            System.out.println("Correo enviado a: " + correo + 
                " con enlace: /reset-password?token=" + token);
        }
        
        // Siempre retornar el mismo mensaje (seguridad)
        return ResponseEntity.ok(new com.upc.perulisto.DTO.MessageResponse(
            "Si el correo está registrado, recibirás un enlace de recuperación"));
    }

    public ResponseEntity<?> restablecerPassword(String token, PasswordResetRequest request) {
        Usuario usuario = usuarioRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));
        
        // Validar token no expirado
        if (usuario.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("El enlace ha expirado. Solicita uno nuevo"));
        }
        
        // Validar nueva contraseña
        if (request.getNewPassword().length() < 8) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("La contraseña debe tener al menos 8 caracteres"));
        }
        
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest()
                    .body(new com.upc.perulisto.DTO.MessageResponse("Las contraseñas no coinciden"));
        }
        
        // Actualizar contraseña
        usuario.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        usuario.setResetToken(null);
        usuario.setResetTokenExpiry(null);
        usuarioRepository.save(usuario);
        
        return ResponseEntity.ok(new com.upc.perulisto.DTO.MessageResponse("Contraseña actualizada exitosamente"));
    }

}
