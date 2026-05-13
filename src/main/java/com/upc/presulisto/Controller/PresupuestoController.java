package com.upc.presulisto.Controller;

import com.upc.presulisto.DTO.PresupuestoDTO;
import com.upc.presulisto.services.PresupuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/API")

public class PresupuestoController {

    @Autowired
    private PresupuestoService presupuestoService;

    // HU-21: Crear presupuesto
    @PostMapping("/crear_presupuesto")
    public PresupuestoDTO crearPresupuesto(@RequestBody PresupuestoDTO presupuestoDTO) {
        return presupuestoService.crearPresupuesto(presupuestoDTO);
    }

    // HU-22: Listar presupuestos por usuario, mes y año
    @GetMapping("/listar_presupuestos")
    public List<PresupuestoDTO> listarPresupuestos(@RequestParam Long usuarioId,
                                                   @RequestParam int mes,
                                                   @RequestParam int anio) {
        return presupuestoService.listarPresupuestos(usuarioId, mes, anio);
    }

    // HU-23: Editar presupuesto (solo monto)
    @PutMapping("/editar_presupuesto/{id}")
    public PresupuestoDTO editarPresupuesto(@PathVariable Long id, @RequestParam BigDecimal monto) {
        return presupuestoService.editarPresupuesto(id, monto);
    }

    // HU-24: Eliminar presupuesto
    @DeleteMapping("/eliminar_presupuesto/{id}")
    public ResponseEntity<?> eliminarPresupuesto(@PathVariable Long id) {
        return presupuestoService.eliminarPresupuesto(id);
    }

}
