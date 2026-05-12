package com.upc.perulisto.services;

import com.upc.perulisto.DTO.ArticuloEducacionDTO;
import com.upc.perulisto.entidades.ArticuloEducacion;
import com.upc.perulisto.entidades.Deuda;
import com.upc.perulisto.repositorio.ArticuloEducacionRepository;
import com.upc.perulisto.repositorio.DeudaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticuloEducacionService {

    @Autowired
    private ArticuloEducacionRepository articuloEducacionRepository;

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // HU-16: Listar artículos (simplified pagination - return all + manual pagination)
    public List<ArticuloEducacionDTO> listarArticulos(int page, int size) {
        List<ArticuloEducacion> articulos = articuloEducacionRepository.findAllByOrderByFechaPublicacionDesc();

        // Manual pagination
        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, articulos.size());

        if (startIndex >= articulos.size()) {
            return new ArrayList<>();
        }

        return articulos.subList(startIndex, endIndex).stream()
                .map(a -> modelMapper.map(a, ArticuloEducacionDTO.class))
                .collect(Collectors.toList());
    }

    // HU-17: Ver artículo por ID
    public ArticuloEducacionDTO getArticulo(Long id) {
        ArticuloEducacion articulo = articuloEducacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));
        return modelMapper.map(articulo, ArticuloEducacionDTO.class);
    }

    // HU-29: Filtrar artículos por categoría
    public List<ArticuloEducacionDTO> filtrarPorCategoria(String categoria) {
        return articuloEducacionRepository.findByCategoriaTematica(categoria).stream()
                .map(a -> modelMapper.map(a, ArticuloEducacionDTO.class))
                .collect(Collectors.toList());
    }

    // HU-30: Sugerir artículos basado en el perfil del usuario
    public List<ArticuloEducacionDTO> sugerirArticulos(Long usuarioId) {
        List<Deuda> deudas = deudaRepository.findByUsuarioId(usuarioId);

        String categoriaSugerida;

        // Si tiene deudas → sugerir artículos de "deudas"
        if (!deudas.isEmpty()) {
            categoriaSugerida = "deudas";
        } else {
            // Verificar si tiene balance negativo (gastos > ingresos)
            // Por simplicidad, retornamos los últimos 3 artículos generales
            categoriaSugerida = null;
        }

        List<ArticuloEducacion> articulos;
        if (categoriaSugerida != null) {
            articulos = articuloEducacionRepository.findByCategoriaTematica(categoriaSugerida);
        } else {
            // Retornar los 3 artículos más recientes
            articulos = articuloEducacionRepository.findAllByOrderByFechaPublicacionDesc();
            if (articulos.size() > 3) {
                articulos = articulos.subList(0, 3);
            }
        }

        return articulos.stream()
                .map(a -> modelMapper.map(a, ArticuloEducacionDTO.class))
                .collect(Collectors.toList());
    }
}
