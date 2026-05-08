package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.GastoDTO;
import com.upc.perulisto.services.GastoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/API")

public class GastoController {

    @Autowired
    private GastoService gastoService;
/*
    @PostMapping("/registrar_gasto")
    public GastoDTO registrarGasto(@RequestBody GastoDTO gastoDTO) {
        return gastoService.registrarGasto(gastoDTO);
    }

    @GetMapping("/listar_gastos")
    public List<GastoDTO> listarGastos() {
        return gastoService.listarGastos();
    }

    @PutMapping("/editar_gasto/{id}")
    public GastoDTO editarGasto(@PathVariable Long id, @RequestBody GastoDTO gastoDTO) {
        return gastoService.editarGasto(id, gastoDTO);
    }

    @DeleteMapping("/eliminar_gasto/{id}")
    public void eliminarGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
    }

*/
     // 1. Registrar un gasto (Con @Valid para activar las validaciones)
    @PostMapping("/registrar_gasto")
    public ResponseEntity<GastoDTO> registrarGasto(@Valid @RequestBody GastoDTO gastoDTO) {
        return new ResponseEntity<>(gastoService.registrarGasto(gastoDTO), HttpStatus.CREATED);
    }

    // 2. Listar todos los gastos
    @GetMapping("/ver_gastos")
    public ResponseEntity<List<GastoDTO>> listarGastos() {
        return ResponseEntity.ok(gastoService.listarGastos());
    }

    // 3. Editar un gasto existente
    @PutMapping("/modificar_gasto/{id}")
    public ResponseEntity<GastoDTO> editarGasto(@PathVariable Long id, @Valid @RequestBody GastoDTO dto) {
        return ResponseEntity.ok(gastoService.editarGasto(id, dto));
    }

    // 4. Eliminar un gasto
    @DeleteMapping("/eliminar_gasto/{id}")
    public ResponseEntity<Void> eliminarGasto(@PathVariable Long id) {
        gastoService.eliminarGasto(id);
        return ResponseEntity.noContent().build();
    }

    // 5. Filtrar por Categoría
    @GetMapping("/filtro/categoria")
    public ResponseEntity<List<GastoDTO>> filtrarPorCategoria(@RequestParam Long id) {
        return ResponseEntity.ok(gastoService.filtrarPorCategoria(id));
    }

    // 6. Filtrar por Rango de Fechas
    @GetMapping("/filtro/fechas")
    public ResponseEntity<List<GastoDTO>> filtrarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(gastoService.filtrarPorFecha(inicio, fin));
    }





}
