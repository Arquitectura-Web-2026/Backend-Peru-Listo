package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.JwtResponse;
import com.upc.perulisto.DTO.LoginRequest;
import com.upc.perulisto.DTO.MessageResponse;
import com.upc.perulisto.DTO.PasswordResetRequest;
import com.upc.perulisto.entidades.Usuario;
import com.upc.perulisto.repositorio.UsuarioRepository;
import com.upc.perulisto.security.JwtUtils;
import com.upc.perulisto.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/API")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioRepository.findByCorreo(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                "Bearer",
                usuario.getId(),
                usuario.getCorreo(),
                usuario.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (usuarioRepository.existsByCorreo(loginRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: El correo ya está en uso"));
        }

        Usuario usuario = new Usuario();
        usuario.setCorreo(loginRequest.getEmail());
        usuario.setPasswordHash(passwordEncoder.encode(loginRequest.getPassword()));

        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MessageResponse("Usuario registrado exitosamente"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(new MessageResponse("Sesión cerrada exitosamente"));
    }

    @PostMapping("/solicitar_recuperacion")
    public ResponseEntity<?> solicitarRecuperacion(@RequestParam String correo) {
        return usuarioService.solicitarRecuperacion(correo);
    }

    @PostMapping("/restablecer_password")
    public ResponseEntity<?> restablecerPassword(@RequestParam String token, 
                                                  @RequestBody PasswordResetRequest request) {
        return usuarioService.restablecerPassword(token, request);
    }
}
