package com.upc.perulisto.repositorio;

import com.upc.perulisto.entiidades.Deuda;
import com.upc.perulisto.entiidades.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GastoRepository extends JpaRepository<Gasto, Long>  {
}
