package com.upc.perulisto.repositorio;

import com.upc.perulisto.entidades.Presupuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    // HU-22: Listar presupuestos por usuario, mes y año
    List<Presupuesto> findByUsuarioIdAndMesAndAnio(Long usuarioId, int mes, int anio);

    // HU-22: Listar presupuestos ordenados por categoría
    List<Presupuesto> findByUsuarioIdAndMesAndAnioOrderByCategoriaNombre(Long usuarioId, int mes, int anio);

}
