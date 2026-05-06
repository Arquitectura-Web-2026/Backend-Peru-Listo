package com.upc.perulisto.Controller;


import com.upc.perulisto.DTO.UsuarioDTO;
import com.upc.perulisto.entiidades.Usuario;
import com.upc.perulisto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController


@RequestMapping("/API")



public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registrar_usuario")
    public UsuarioDTO registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.registrarUsuario(usuarioDTO);

    }
    @GetMapping("/listar_usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @PutMapping("/editar_usuario/{id}")
    public UsuarioDTO editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editarUsuario(id, usuarioDTO);
    }

    @DeleteMapping("/eliminar_usuario/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }


}
