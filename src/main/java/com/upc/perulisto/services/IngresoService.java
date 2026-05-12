package com.upc.perulisto.services;

import com.upc.perulisto.DTO.IngresoDTO;
import com.upc.perulisto.DTO.MessageResponse;
import com.upc.perulisto.entidades.Ingreso;
import com.upc.perulisto.entidades.Usuario;
import com.upc.perulisto.repositorio.IngresoRepository;
import com.upc.perulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngresoService {


    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public IngresoDTO registrarIngreso(IngresoDTO ingresoDTO) {
        // Validar que el usuario exista ANTES de guardar (evita quemar IDs)
        Usuario usuario = usuarioRepository.findById(ingresoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Ingreso ingreso = modelMapper.map(ingresoDTO, Ingreso.class);
        ingreso.setUsuario(usuario);
        ingreso = ingresoRepository.save(ingreso);
        return modelMapper.map(ingreso, IngresoDTO.class);
    }

    public List<IngresoDTO> listarIngresos() {
        return ingresoRepository.findAll().stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .toList();
    }

    // HU-07: Listar ingresos por usuario
    public List<IngresoDTO> listarIngresosPorUsuario(Long usuarioId) {
        return ingresoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-07: Listar ingresos por mes y año
    public List<IngresoDTO> listarIngresosPorMesYAnio(Long usuarioId, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        return ingresoRepository.findByUsuarioIdAndFechaBetweenOrderByFechaDesc(usuarioId, fechaInicio, fechaFin).stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-19: Buscar ingresos por descripción (usuario)
    public List<IngresoDTO> buscarIngresosPorUsuario(Long usuarioId, String q) {
        return ingresoRepository.findByUsuarioIdAndDescripcionContainingIgnoreCaseOrderByFechaDesc(usuarioId, q).stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-19: Buscar ingresos por descripción con filtro de mes/año
    public List<IngresoDTO> buscarIngresosPorMesYAnio(Long usuarioId, String q, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);
        return ingresoRepository.findByUsuarioIdAndDescripcionContainingIgnoreCaseAndFechaBetweenOrderByFechaDesc(
                usuarioId, q, fechaInicio, fechaFin).stream()
                .map(i -> modelMapper.map(i, IngresoDTO.class))
                .collect(Collectors.toList());
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

    public ResponseEntity<?> eliminarIngreso(Long id) {
        if (!ingresoRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Ingreso no encontrado"));
        }
        ingresoRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Ingreso eliminado correctamente"));
    }


}
