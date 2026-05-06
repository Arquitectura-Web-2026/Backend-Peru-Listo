package com.upc.perulisto.services;


import com.upc.perulisto.DTO.UsuarioDTO;
import com.upc.perulisto.entiidades.Usuario;
import com.upc.perulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        usuario = usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioDTO.class);

    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario-> modelMapper.map(usuario,UsuarioDTO.class))
                .toList();
    }

    public UsuarioDTO editarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuario.setCorreo(usuarioDTO.getCorreo());

        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }




}
