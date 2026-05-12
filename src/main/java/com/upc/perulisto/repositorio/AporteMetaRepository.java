package com.upc.perulisto.repositorio;

import com.upc.perulisto.entidades.AporteMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AporteMetaRepository extends JpaRepository<AporteMeta, Long> {

    // HU-27: Listar aportes por meta
    List<AporteMeta> findByMetaId(Long metaId);

}
