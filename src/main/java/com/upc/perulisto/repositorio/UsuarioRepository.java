package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.Deuda;
import com.upc.perulisto.entiidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
