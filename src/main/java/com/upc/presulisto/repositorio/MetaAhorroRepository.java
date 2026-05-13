package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.MetaAhorro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetaAhorroRepository extends JpaRepository<MetaAhorro, Long> {

    // HU-26: Listar metas por usuario y estado
    List<MetaAhorro> findByUsuarioIdAndEstado(Long usuarioId, String estado);

    // HU-26: Listar todas las metas por usuario
    List<MetaAhorro> findByUsuarioId(Long usuarioId);

}
