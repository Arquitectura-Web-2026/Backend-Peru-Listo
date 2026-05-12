package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.*;
import com.upc.perulisto.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;

    // ==================== Épica 8: Gestión de Contenido Educativo ====================

    // HU-35: Crear artículo educativo
    @PostMapping("/articulos")
    public ArticuloEducacionDTO crearArticulo(@RequestBody ArticuloEducacionDTO dto) {
        return adminService.crearArticulo(dto);
    }

    // HU-36: Editar artículo educativo
    @PutMapping("/articulos/{id}")
    public ArticuloEducacionDTO editarArticulo(@PathVariable Long id, @RequestBody ArticuloEducacionDTO dto) {
        return adminService.editarArticulo(id, dto);
    }

    // HU-37: Eliminar artículo educativo
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<?> eliminarArticulo(@PathVariable Long id) {
        return adminService.eliminarArticulo(id);
    }

    // ==================== Épica 9: Administración del Sistema ====================

    // HU-38: Dashboard de administrador
    @GetMapping("/dashboard")
    public AdminDashboardDTO getDashboard() {
        return adminService.getDashboard();
    }

    // HU-39: Listar usuarios (Admin)
    @GetMapping("/usuarios")
    public List<AdminUsuarioDTO> listarUsuarios() {
        return adminService.listarUsuarios();
    }

    // HU-39: Ver detalle de usuario (Admin)
    @GetMapping("/usuarios/{id}")
    public AdminUsuarioDetalleDTO getUsuarioDetalle(@PathVariable Long id) {
        return adminService.getUsuarioDetalle(id);
    }
}
