package com.upc.perulisto.services;

import com.upc.perulisto.DTO.GastoDTO;
import com.upc.perulisto.DTO.MessageResponse;
import com.upc.perulisto.entidades.CategoriaGasto;
import com.upc.perulisto.entidades.Gasto;
import com.upc.perulisto.entidades.Usuario;
import com.upc.perulisto.repositorio.CategoriaGastoRepository;
import com.upc.perulisto.repositorio.GastoRepository;
import com.upc.perulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaGastoRepository categoriaGastoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public GastoDTO registrarGasto(GastoDTO dto) {
        // Validar que existan las entidades relacionadas ANTES de guardar (evita quemar IDs)
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        CategoriaGasto categoria = categoriaGastoRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Gasto gasto = modelMapper.map(dto, Gasto.class);
        gasto.setUsuario(usuario);
        gasto.setCategoria(categoria);
        gasto.setFechaCreacion(LocalTime.now());
        gasto = gastoRepository.save(gasto);
        return modelMapper.map(gasto, GastoDTO.class);
    }

    public List<GastoDTO> listarGastos() {
        return gastoRepository.findAll().stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .toList();
    }

    // HU-07: Listar gastos por usuario
    public List<GastoDTO> listarGastosPorUsuario(Long usuarioId) {
        return gastoRepository.findByUsuarioIdOrderByFechaGastoDesc(usuarioId).stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-07: Listar gastos por mes y año
    public List<GastoDTO> listarGastosPorMesYAnio(Long usuarioId, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        return gastoRepository.findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(usuarioId, fechaInicio, fechaFin).stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-19: Buscar gastos por descripción
    public List<GastoDTO> buscarGastosPorUsuario(Long usuarioId, String q) {
        return gastoRepository.findByUsuarioIdAndDescripcionContainingIgnoreCaseOrderByFechaGastoDesc(usuarioId, q).stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-19: Buscar gastos por descripción con filtro de mes/año
    public List<GastoDTO> buscarGastosPorMesYAnio(Long usuarioId, String q, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        return gastoRepository.findByUsuarioIdAndDescripcionContainingIgnoreCaseAndFechaGastoBetweenOrderByFechaGastoDesc(
                usuarioId, q, fechaInicio, fechaFin).stream()
                .map(g -> modelMapper.map(g, GastoDTO.class))
                .collect(Collectors.toList());
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

    public ResponseEntity<?> eliminarGasto(Long id) {
        if (!gastoRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Gasto no encontrado"));
        }
        gastoRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Gasto eliminado correctamente"));
    }

}
