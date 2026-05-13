package com.upc.presulisto.Controller;


import com.upc.presulisto.DTO.PasswordChangeRequest;
import com.upc.presulisto.DTO.UsuarioDTO;
import com.upc.presulisto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController


@RequestMapping("/API")



public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // HU-04: Visualización de perfil
    @GetMapping("/perfil/{id}")
    public UsuarioDTO obtenerPerfil(@PathVariable Long id) {
        return usuarioService.obtenerPerfil(id);
    }

    // HU-04: Actualización de perfil
    @PutMapping("/actualizar_perfil/{id}")
    public UsuarioDTO actualizarPerfil(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.actualizarPerfil(id, usuarioDTO);
    }

    // HU-05: Cambio de contraseña
    @PutMapping("/cambiar_password/{id}")
    public ResponseEntity<?> cambiarPassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        return usuarioService.cambiarPassword(id, request);
    }

}
