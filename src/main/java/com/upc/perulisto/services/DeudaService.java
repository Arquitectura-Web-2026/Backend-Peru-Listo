package com.upc.perulisto.services;


import com.upc.perulisto.DTO.DeudaDTO;
import com.upc.perulisto.entiidades.Deuda;
import com.upc.perulisto.repositorio.DeudaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeudaService {


    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DeudaDTO registrarDeuda(DeudaDTO deudaDTO) {
        Deuda deuda = modelMapper.map(deudaDTO, Deuda.class);
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
        deuda.setEstado(deudaDTO.getEstado());

        deuda = deudaRepository.save(deuda);
        return modelMapper.map(deuda, DeudaDTO.class);
    }

    public void eliminarDeuda(Long id) {
        if (!deudaRepository.existsById(id)) {
            throw new RuntimeException("Deuda no encontrada");
        }
        deudaRepository.deleteById(id);
    }
}



