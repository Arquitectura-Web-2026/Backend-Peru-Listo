package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeudaRepository extends JpaRepository<Deuda, Long> {

    // HU-30: Verificar si el usuario tiene deudas
    List<Deuda> findByUsuarioId(Long usuarioId);
}
