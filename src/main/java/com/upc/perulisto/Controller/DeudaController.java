package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.DeudaDTO;
import com.upc.perulisto.services.DeudaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")

public class DeudaController {

    @Autowired
    private DeudaService deudaService;

    // HU-14: Registro de deuda
    @PostMapping("/registrar_deuda")
    public DeudaDTO registrarDeuda(@RequestBody DeudaDTO deudaDTO) {
        return deudaService.registrarDeuda(deudaDTO);
    }

    // HU-15: Lista de deudas
    @GetMapping("/listar_deudas")
    public List<DeudaDTO> listarDeudas() {
        return deudaService.listarDeudas();
    }

    // HU-17: Edición de deuda
    @PutMapping("/editar_deuda/{id}")
    public DeudaDTO editarDeuda(@PathVariable Long id, @RequestBody DeudaDTO deudaDTO) {
        return deudaService.editarDeuda(id, deudaDTO);
    }

    // HU-17: Eliminación de deuda
    @DeleteMapping("/eliminar_deuda/{id}")
    public ResponseEntity<?> eliminarDeuda(@PathVariable Long id) {
        return deudaService.eliminarDeuda(id);
    }

    // HU-16: Marcar deuda como pagada
    @PutMapping("/marcar_pagada/{id}")
    public DeudaDTO marcarDeudaPagada(@PathVariable Long id) {
        return deudaService.marcarDeudaPagada(id);
    }

}
