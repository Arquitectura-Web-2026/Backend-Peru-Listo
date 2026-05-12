package com.upc.perulisto.services;

import com.upc.perulisto.DTO.CategoriaDTO;
import com.upc.perulisto.DTO.MessageResponse;
import com.upc.perulisto.entidades.CategoriaGasto;
import com.upc.perulisto.repositorio.CategoriaGastoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaGastoService {

    @Autowired
    private CategoriaGastoRepository categoriaGastoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CategoriaDTO> listarCategoriasPorUsuario(Long usuarioId) {
        return categoriaGastoRepository.findByUsuarioIdOrEsPredeterminadaTrue(usuarioId).stream()
                .map(c -> modelMapper.map(c, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    public CategoriaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        CategoriaGasto categoria = modelMapper.map(categoriaDTO, CategoriaGasto.class);
        categoria.setEsPredeterminada(false);
        categoria.setTipo("gasto");
        categoria = categoriaGastoRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }

    public ResponseEntity<?> eliminarCategoria(Long id, Long usuarioId) {
        CategoriaGasto categoria = categoriaGastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        if (categoria.isEsPredeterminada()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: No se puede eliminar una categoría predeterminada"));
        }

        if (categoria.getUsuario() == null || !categoria.getUsuario().getId().equals(usuarioId)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: No tiene permiso para eliminar esta categoría"));
        }

        if (categoria.getGastos() != null && !categoria.getGastos().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: No se puede eliminar la categoría porque tiene transacciones asociadas"));
        }

        categoriaGastoRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Categoría eliminada correctamente"));
    }
}
