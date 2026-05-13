package com.upc.presulisto.Controller;

import com.upc.presulisto.DTO.ArticuloEducacionDTO;
import com.upc.presulisto.services.ArticuloEducacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API")
public class ArticuloEducacionController {

    @Autowired
    private ArticuloEducacionService articuloEducacionService;

    // HU-16: Listar artículos con paginación simplificada
    @GetMapping("/listar_articulos")
    public List<ArticuloEducacionDTO> listarArticulos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return articuloEducacionService.listarArticulos(page, size);
    }

    // HU-17: Ver artículo por ID
    @GetMapping("/ver_articulo/{id}")
    public ArticuloEducacionDTO verArticulo(@PathVariable Long id) {
        return articuloEducacionService.getArticulo(id);
    }

    // HU-29: Filtrar artículos por categoría
    @GetMapping("/filtrar_articulos")
    public List<ArticuloEducacionDTO> filtrarArticulos(
            @RequestParam String categoria) {
        return articuloEducacionService.filtrarPorCategoria(categoria);
    }

    // HU-30: Sugerir artículos basado en el usuario
    @GetMapping("/sugerir_articulos")
    public List<ArticuloEducacionDTO> sugerirArticulos(
            @RequestParam Long usuarioId) {
        return articuloEducacionService.sugerirArticulos(usuarioId);
    }
}
