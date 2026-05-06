package com.upc.perulisto.services;


import com.upc.perulisto.DTO.GastoDTO;
import com.upc.perulisto.entiidades.Gasto;
import com.upc.perulisto.repositorio.GastoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GastoDTO registrarGasto(GastoDTO dto) {
        Gasto gasto = modelMapper.map(dto, Gasto.class);
        gasto = gastoRepository.save(gasto);
        return modelMapper.map(gasto, GastoDTO.class);
    }

    public List<GastoDTO> listarGastos() {
        return gastoRepository.findAll().stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .toList();
    }

    public GastoDTO editarGasto(Long id, GastoDTO gastoDTO) {
        Gasto gasto = gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto no encontrado"));

        gasto.setDescripcion(gastoDTO.getDescripcion());
        gasto.setMonto(gastoDTO.getMonto());
        gasto.setFechaGasto(gastoDTO.getFechagasto());


        gasto = gastoRepository.save(gasto);
        return modelMapper.map(gasto, GastoDTO.class);
    }

    public void eliminarGasto(Long id) {
        if (!gastoRepository.existsById(id)) {
            throw new RuntimeException("Gasto no encontrado");
        }
        gastoRepository.deleteById(id);
    }


}
