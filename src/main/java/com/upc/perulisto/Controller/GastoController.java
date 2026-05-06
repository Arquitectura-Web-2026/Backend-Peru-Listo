package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.GastoDTO;
import com.upc.perulisto.services.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")

public class GastoController {

    @Autowired
    private GastoService gastoService;

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



}
