package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.CategoriaGasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaGastoRepository extends JpaRepository<CategoriaGasto, Long> {
}
