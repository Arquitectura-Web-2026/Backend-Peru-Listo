package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.CategoriaGasto;
import com.upc.perulisto.entiidades.Deuda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeudaRepository extends JpaRepository<Deuda, Long> {
}
