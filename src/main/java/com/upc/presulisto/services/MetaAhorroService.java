package com.upc.presulisto.services;

import com.upc.presulisto.DTO.MessageResponse;
import com.upc.presulisto.DTO.MetaAhorroDTO;
import com.upc.presulisto.entidades.MetaAhorro;
import com.upc.presulisto.entidades.Usuario;
import com.upc.presulisto.repositorio.AporteMetaRepository;
import com.upc.presulisto.repositorio.MetaAhorroRepository;
import com.upc.presulisto.repositorio.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MetaAhorroService {

    @Autowired
    private MetaAhorroRepository metaAhorroRepository;

    @Autowired
    private AporteMetaRepository aporteMetaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    // HU-25: Crear meta de ahorro
    public MetaAhorroDTO crearMeta(MetaAhorroDTO dto) {
        // Validar que el usuario exista ANTES de guardar (evita quemar IDs)
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        MetaAhorro meta = modelMapper.map(dto, MetaAhorro.class);
        meta.setUsuario(usuario);
        meta.setMontoActual(BigDecimal.ZERO);
        meta.setEstado("en_progreso");
        meta.setFechaCreacion(LocalDateTime.now());
        meta = metaAhorroRepository.save(meta);
        return modelMapper.map(meta, MetaAhorroDTO.class);
    }

    // HU-26: Listar metas por usuario
    public List<MetaAhorroDTO> listarMetas(Long usuarioId) {
        return metaAhorroRepository.findByUsuarioId(usuarioId).stream()
                .map(m -> modelMapper.map(m, MetaAhorroDTO.class))
                .collect(Collectors.toList());
    }

    // HU-26: Listar metas en progreso por usuario
    public List<MetaAhorroDTO> listarMetasEnProgreso(Long usuarioId) {
        return metaAhorroRepository.findByUsuarioIdAndEstado(usuarioId, "en_progreso").stream()
                .map(m -> modelMapper.map(m, MetaAhorroDTO.class))
                .collect(Collectors.toList());
    }

    // HU-27: Aportar a una meta
    public MetaAhorroDTO aportarMeta(Long metaId, BigDecimal monto) {
        MetaAhorro meta = metaAhorroRepository.findById(metaId)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada"));

        // Sumar el monto al actual
        meta.setMontoActual(meta.getMontoActual().add(monto));

        // Verificar si se completó la meta
        if (meta.getMontoActual().compareTo(meta.getMontoObjetivo()) >= 0) {
            meta.setEstado("completada");
        }

        meta = metaAhorroRepository.save(meta);
        return modelMapper.map(meta, MetaAhorroDTO.class);
    }

    // HU-28: Editar meta de ahorro
    public MetaAhorroDTO editarMeta(Long id, MetaAhorroDTO metaDTO) {
        MetaAhorro meta = metaAhorroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Meta no encontrada"));

        meta.setNombre(metaDTO.getNombre());
        meta.setMontoObjetivo(metaDTO.getMontoObjetivo());
        meta.setFechaLimite(metaDTO.getFechaLimite());

        meta = metaAhorroRepository.save(meta);
        return modelMapper.map(meta, MetaAhorroDTO.class);
    }

    // HU-28: Eliminar meta de ahorro (también elimina los aportes)
    public ResponseEntity<?> eliminarMeta(Long id) {
        if (!metaAhorroRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Meta no encontrada"));
        }
        // Los aportes se eliminan automáticamente si hay cascade, sino hay que eliminarlos manualmente
        metaAhorroRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Meta eliminada correctamente"));
    }

}
