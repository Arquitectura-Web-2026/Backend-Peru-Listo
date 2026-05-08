package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.CategoriaGasto;
import com.upc.perulisto.entiidades.Deuda;
import com.upc.perulisto.entiidades.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GastoRepository extends JpaRepository<Gasto, Long>  {

    // 1. FILTRAR POR CATEGORIA
    List<Gasto>  findByCategoriaId(Long categoriaId);

    // 2. Filtrar por Rango de Fechas
    List<Gasto> findByFechaGastoBetween(LocalDate inicio, LocalDate fin);

    // 3. Filtrar por Método de Pago (Asumiendo que agregaste el campo 'metodoPago' a la entidad)
   //List<Gasto> findByMetodoPago(String metodoPago);

    // Filtro Combinado: Por Usuario y Rango de Fechas (Muy útil)
    List<Gasto> findByUsuarioIdAndFechaGastoBetween(Long usuarioId, LocalDate inicio, LocalDate fin);



}
