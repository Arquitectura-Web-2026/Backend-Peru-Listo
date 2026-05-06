package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.DeudaDTO;
import com.upc.perulisto.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")

public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    @PostMapping("/registrar_deuda")
    public DeudaDTO registrarDeuda(@RequestBody DeudaDTO deudaDTO) {
        return deudaService.registrarDeuda(deudaDTO);
    }

    @GetMapping("/listar_deudas")
    public List<DeudaDTO> listarDeudas() {
        return deudaService.listarDeudas();
    }

    @PutMapping("/editar_deuda/{id}")
    public DeudaDTO editarDeuda(@PathVariable Long id, @RequestBody DeudaDTO deudaDTO) {
        return deudaService.editarDeuda(id, deudaDTO);
    }

    @DeleteMapping("/eliminar_deuda/{id}")
    public void eliminarDeuda(@PathVariable Long id) {
        deudaService.eliminarDeuda(id);
    }


}
