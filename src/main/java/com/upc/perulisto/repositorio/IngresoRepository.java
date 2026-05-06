package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.Deuda;
import com.upc.perulisto.entiidades.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngresoRepository extends JpaRepository<Ingreso, Long> {
}
