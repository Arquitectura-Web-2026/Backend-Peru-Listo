package com.upc.perulisto.services;

import com.upc.perulisto.DTO.IngresoDTO;
import com.upc.perulisto.entiidades.Ingreso;
import com.upc.perulisto.repositorio.IngresoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngresoService {


    @Autowired
    private IngresoRepository ingresoRepository;


    @Autowired
    private ModelMapper modelMapper;

    public IngresoDTO registrarIngreso(IngresoDTO ingresoDTO) {
        Ingreso ingreso = modelMapper.map(ingresoDTO, Ingreso.class);
        ingreso = ingresoRepository.save(ingreso);
        return modelMapper.map(ingreso, IngresoDTO.class);
    }

    public List<IngresoDTO> listarIngresos() {
        return ingresoRepository.findAll().stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .toList();
    }

    public IngresoDTO editarIngreso(Long id, IngresoDTO ingresoDTO) {
        Ingreso ingreso = ingresoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingreso no encontrado"));

        ingreso.setDescripcion(ingresoDTO.getDescripcion());
        ingreso.setMonto(ingresoDTO.getMonto());
        ingreso.setFecha(ingresoDTO.getFecha());

        ingreso = ingresoRepository.save(ingreso);
        return modelMapper.map(ingreso, IngresoDTO.class);
    }

    public void eliminarIngreso(Long id) {
        if (!ingresoRepository.existsById(id)) {
            throw new RuntimeException("Ingreso no encontrado");
        }
        ingresoRepository.deleteById(id);
    }


}
