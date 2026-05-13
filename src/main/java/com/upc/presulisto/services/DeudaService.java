package com.upc.presulisto.services;


import com.upc.presulisto.DTO.DeudaDTO;
import com.upc.presulisto.DTO.MessageResponse;
import com.upc.presulisto.entidades.Deuda;
import com.upc.presulisto.entidades.Usuario;
import com.upc.presulisto.repositorio.DeudaRepository;
import com.upc.presulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaService {


    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DeudaDTO registrarDeuda(DeudaDTO deudaDTO) {
        // Validar que el usuario exista ANTES de guardar (evita quemar IDs)
        Usuario usuario = usuarioRepository.findById(deudaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Deuda deuda = modelMapper.map(deudaDTO, Deuda.class);
        deuda.setUsuario(usuario);
        deuda.setEstado("Pendiente");
        deuda = deudaRepository.save(deuda);
        return modelMapper.map(deuda, DeudaDTO.class);
    }

    public List<DeudaDTO> listarDeudas() {
        return deudaRepository.findAll().stream()
                .map(d -> modelMapper.map(d, DeudaDTO.class))
                .toList();
    }

    public DeudaDTO editarDeuda(Long id, DeudaDTO deudaDTO) {
        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));

        deuda.setAcreedor(deudaDTO.getAcreedor());
        deuda.setMonto(deudaDTO.getMonto());
        deuda.setFechaLimite(deudaDTO.getFechaLimite());
        // NOTA: No se setea estado aquí — solo se cambia vía marcar_pagada (HU-16)

        deuda = deudaRepository.save(deuda);
        return modelMapper.map(deuda, DeudaDTO.class);
    }

    public ResponseEntity<?> eliminarDeuda(Long id) {
        if (!deudaRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Deuda no encontrada"));
        }
        deudaRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Deuda eliminada correctamente"));
    }

    public DeudaDTO marcarDeudaPagada(Long id) {
        Deuda deuda = deudaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deuda no encontrada"));

        deuda.setEstado("pagada");
        deuda = deudaRepository.save(deuda);
        return modelMapper.map(deuda, DeudaDTO.class);
    }
}



