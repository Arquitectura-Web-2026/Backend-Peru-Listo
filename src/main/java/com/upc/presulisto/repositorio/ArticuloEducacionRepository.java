package com.upc.presulisto.repositorio;

import com.upc.presulisto.entidades.ArticuloEducacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloEducacionRepository extends JpaRepository<ArticuloEducacion, Long> {

    // HU-29: Filtrar artículos por categoría temática
    List<ArticuloEducacion> findByCategoriaTematica(String categoriaTematica);

    // HU-16: Listar artículos ordenados por fecha de publicación (más recientes primero)
    List<ArticuloEducacion> findAllByOrderByFechaPublicacionDesc();
}
