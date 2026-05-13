package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {

    // HU-07: Listar ingresos por usuario, ordenado por fecha descendente
    List<Ingreso> findByUsuarioIdOrderByFechaDesc(Long usuarioId);

    // HU-07: Listar ingresos por usuario y rango de fechas
    List<Ingreso> findByUsuarioIdAndFechaBetweenOrderByFechaDesc(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin);

    // HU-19: Buscar ingresos por descripción (case insensitive)
    List<Ingreso> findByUsuarioIdAndDescripcionContainingIgnoreCaseOrderByFechaDesc(Long usuarioId, String descripcion);

    // HU-19: Buscar ingresos por descripción y rango de fechas
    List<Ingreso> findByUsuarioIdAndDescripcionContainingIgnoreCaseAndFechaBetweenOrderByFechaDesc(
            Long usuarioId, String descripcion, LocalDate fechaInicio, LocalDate fechaFin);
}
