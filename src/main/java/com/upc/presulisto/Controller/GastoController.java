package com.upc.presulisto.Controller;

import com.upc.presulisto.DTO.CategoriaDTO;
import com.upc.presulisto.DTO.GastoDTO;
import com.upc.presulisto.services.CategoriaGastoService;
import com.upc.presulisto.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")

public class GastoController {

    @Autowired
    private GastoService gastoService;

    @Autowired
    private CategoriaGastoService categoriaGastoService;

    // HU-06: Registro de gasto
    @PostMapping("/registrar_gasto")
    public GastoDTO registrarGasto(@RequestBody GastoDTO gastoDTO) {
        return gastoService.registrarGasto(gastoDTO);
    }

    // HU-07: Listar gastos con filtro por mes/año
    @GetMapping("/listar_gastos")
    public List<GastoDTO> listarGastos(@RequestParam Long usuarioId,
                                       @RequestParam(required = false) Integer mes,
                                       @RequestParam(required = false) Integer anio) {
        if (mes != null && anio != null) {
            return gastoService.listarGastosPorMesYAnio(usuarioId, mes, anio);
        }
        return gastoService.listarGastosPorUsuario(usuarioId);
    }

    // HU-19: Búsqueda de gastos
    @GetMapping("/buscar_gastos")
    public List<GastoDTO> buscarGastos(@RequestParam Long usuarioId,
                                       @RequestParam String q,
                                       @RequestParam(required = false) Integer mes,
                                       @RequestParam(required = false) Integer anio) {
        if (q.length() < 3) {
            throw new RuntimeException("El término de búsqueda debe tener al menos 3 caracteres");
        }
        if (mes != null && anio != null) {
            return gastoService.buscarGastosPorMesYAnio(usuarioId, q, mes, anio);
        }
        return gastoService.buscarGastosPorUsuario(usuarioId, q);
    }

    // HU-08: Editar gasto
    @PutMapping("/editar_gasto/{id}")
    public GastoDTO editarGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO) {
        return gastoService.editarGasto(id, gastoDTO);
    }

    // HU-09: Eliminar gasto
    @DeleteMapping("/eliminar_gasto/{id}")
    public ResponseEntity<?> eliminarGasto(@PathVariable Long id) {
        return gastoService.eliminarGasto(id);
    }

    // ==================== HU-18: Gestión de categorías ====================

    @GetMapping("/listar_categorias")
    public List<CategoriaDTO> listarCategorias(@RequestParam Long usuarioId) {
        return categoriaGastoService.listarCategoriasPorUsuario(usuarioId);
    }

    @PostMapping("/crear_categoria")
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return categoriaGastoService.crearCategoria(categoriaDTO);
    }

    @DeleteMapping("/eliminar_categoria/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id, @RequestParam Long usuarioId) {
        return categoriaGastoService.eliminarCategoria(id, usuarioId);
    }

}
