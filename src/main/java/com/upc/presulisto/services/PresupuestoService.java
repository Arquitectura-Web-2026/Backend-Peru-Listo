package com.upc.presulisto.services;

import com.upc.presulisto.DTO.MessageResponse;
import com.upc.presulisto.DTO.PresupuestoDTO;
import com.upc.presulisto.entidades.CategoriaGasto;
import com.upc.presulisto.entidades.Presupuesto;
import com.upc.presulisto.entidades.Usuario;
import com.upc.presulisto.repositorio.CategoriaGastoRepository;
import com.upc.presulisto.repositorio.PresupuestoRepository;
import com.upc.presulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PresupuestoService {

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaGastoRepository categoriaGastoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // HU-21: Crear presupuesto (verificar duplicado)
    public PresupuestoDTO crearPresupuesto(PresupuestoDTO dto) {
        // Validar que existan las entidades relacionadas ANTES de guardar (evita quemar IDs)
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        CategoriaGasto categoria = categoriaGastoRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Verificar si ya existe un presupuesto para el mismo usuario, categoría, mes y año
        List<Presupuesto> existentes = presupuestoRepository.findByUsuarioIdAndMesAndAnio(
                dto.getUsuarioId(), dto.getMes(), dto.getAnio());

        boolean duplicado = existentes.stream()
                .anyMatch(p -> p.getCategoria().getId().equals(dto.getCategoriaId()));

        if (duplicado) {
            throw new RuntimeException("Ya existe un presupuesto para esta categoría en el mes y año seleccionado");
        }

        Presupuesto presupuesto = modelMapper.map(dto, Presupuesto.class);
        presupuesto.setUsuario(usuario);
        presupuesto.setCategoria(categoria);
        presupuesto = presupuestoRepository.save(presupuesto);
        return modelMapper.map(presupuesto, PresupuestoDTO.class);
    }

    // HU-22: Listar presupuestos por usuario, mes y año
    public List<PresupuestoDTO> listarPresupuestos(Long usuarioId, int mes, int anio) {
        return presupuestoRepository.findByUsuarioIdAndMesAndAnio(usuarioId, mes, anio).stream()
                .map(p -> modelMapper.map(p, PresupuestoDTO.class))
                .collect(Collectors.toList());
    }

    // HU-23: Editar presupuesto (solo monto)
    public PresupuestoDTO editarPresupuesto(Long id, BigDecimal nuevoMonto) {
        Presupuesto presupuesto = presupuestoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado"));

        presupuesto.setMontoLimite(nuevoMonto);
        presupuesto = presupuestoRepository.save(presupuesto);
        return modelMapper.map(presupuesto, PresupuestoDTO.class);
    }

    // HU-24: Eliminar presupuesto
    public ResponseEntity<?> eliminarPresupuesto(Long id) {
        if (!presupuestoRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Presupuesto no encontrado"));
        }
        presupuestoRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Presupuesto eliminado correctamente"));
    }

}
