package com.upc.presulisto.Controller;

import com.upc.presulisto.DTO.MetaAhorroDTO;
import com.upc.presulisto.services.MetaAhorroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/API")

public class MetaAhorroController {

    @Autowired
    private MetaAhorroService metaAhorroService;

    // HU-25: Crear meta de ahorro
    @PostMapping("/crear_meta")
    public MetaAhorroDTO crearMeta(@RequestBody MetaAhorroDTO metaAhorroDTO) {
        return metaAhorroService.crearMeta(metaAhorroDTO);
    }

    // HU-26: Listar metas por usuario
    @GetMapping("/listar_metas")
    public List<MetaAhorroDTO> listarMetas(@RequestParam Long usuarioId) {
        return metaAhorroService.listarMetas(usuarioId);
    }

    // HU-27: Aportar a una meta
    @PutMapping("/aportar_meta/{metaId}")
    public MetaAhorroDTO aportarMeta(@PathVariable Long metaId, @RequestParam BigDecimal monto) {
        return metaAhorroService.aportarMeta(metaId, monto);
    }

    // HU-28: Editar meta de ahorro
    @PutMapping("/editar_meta/{id}")
    public MetaAhorroDTO editarMeta(@PathVariable Long id, @RequestBody MetaAhorroDTO metaAhorroDTO) {
        return metaAhorroService.editarMeta(id, metaAhorroDTO);
    }

    // HU-28: Eliminar meta de ahorro
    @DeleteMapping("/eliminar_meta/{id}")
    public ResponseEntity<?> eliminarMeta(@PathVariable Long id) {
        return metaAhorroService.eliminarMeta(id);
    }

}
