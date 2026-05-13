package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long>  {

    // HU-07: Listar gastos por usuario, ordenado por fecha descendente
    List<Gasto> findByUsuarioIdOrderByFechaGastoDesc(Long usuarioId);

    // HU-07: Listar gastos por usuario y rango de fechas
    List<Gasto> findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin);

    // HU-19: Buscar gastos por descripción (case insensitive)
    List<Gasto> findByUsuarioIdAndDescripcionContainingIgnoreCaseOrderByFechaGastoDesc(Long usuarioId, String descripcion);

    // HU-19: Buscar gastos por descripción y rango de fechas
    List<Gasto> findByUsuarioIdAndDescripcionContainingIgnoreCaseAndFechaGastoBetweenOrderByFechaGastoDesc(
            Long usuarioId, String descripcion, LocalDate fechaInicio, LocalDate fechaFin);
}
