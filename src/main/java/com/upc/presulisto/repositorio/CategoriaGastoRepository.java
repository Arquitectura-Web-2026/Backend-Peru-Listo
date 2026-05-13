package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {

    @Query("SELECT c FROM CategoriaGasto c WHERE c.usuario.id = :usuarioId OR c.esPredeterminada = true")
    List<CategoriaGasto> findByUsuarioIdOrEsPredeterminadaTrue(@Param("usuarioId") Long usuarioId);

    List<CategoriaGasto> findByUsuarioId(Long usuarioId);
}
