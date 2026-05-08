package com.upc.perulisto.security;

import com.upc.perulisto.entidades.Usuario;
import com.upc.perulisto.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));

        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getCorreo())
                .password(usuario.getPasswordHash())
                .roles(usuario.getRole())
                .build();
    }
}
