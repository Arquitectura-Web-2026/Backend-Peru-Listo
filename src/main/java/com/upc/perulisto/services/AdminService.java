package com.upc.perulisto.services;

import com.upc.perulisto.DTO.*;
import com.upc.perulisto.entidades.*;
import com.upc.perulisto.repositorio.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private DeudaRepository deudaRepository;

    @Autowired
    private MetaAhorroRepository metaAhorroRepository;

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Autowired
    private ArticuloEducacionRepository articuloEducacionRepository;

    @Autowired
    private ModelMapper modelMapper;

    // ========== HU-38: Dashboard de administrador ==========

    public AdminDashboardDTO getDashboard() {
        // Total de usuarios registrados
        long totalUsuarios = usuarioRepository.count();

        // Usuarios nuevos este mes
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        long usuariosNuevosEsteMes = usuarioRepository.findAll().stream()
                .filter(u -> u.getFechaRegistro() != null && !u.getFechaRegistro().isBefore(inicioMes))
                .count();

        // Total de transacciones
        long totalGastos = gastoRepository.count();
        long totalIngresos = ingresoRepository.count();
        long totalTransacciones = totalGastos + totalIngresos;

        // Suma total de gastos e ingresos
        double totalGastosSistema = gastoRepository.findAll().stream()
                .mapToDouble(Gasto::getMonto)
                .sum();
        double totalIngresosSistema = ingresoRepository.findAll().stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();

        // Deudas pendientes
        long totalDeudasPendientes = deudaRepository.findAll().stream()
                .filter(d -> "Pendiente".equals(d.getEstado()))
                .count();

        // Metas de ahorro activas
        long totalMetasAhorro = metaAhorroRepository.findAll().stream()
                .filter(m -> "en_progreso".equals(m.getEstado()))
                .count();

        return new AdminDashboardDTO(
                totalUsuarios,
                usuariosNuevosEsteMes,
                totalTransacciones,
                totalGastosSistema,
                totalIngresosSistema,
                totalDeudasPendientes,
                totalMetasAhorro
        );
    }

    // ========== HU-35: Crear artículo educativo ==========

    public ArticuloEducacionDTO crearArticulo(ArticuloEducacionDTO dto) {
        // Validar campos obligatorios
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new RuntimeException("El título del artículo es obligatorio");
        }
        if (dto.getCuerpo() == null || dto.getCuerpo().trim().isEmpty()) {
            throw new RuntimeException("El cuerpo del artículo es obligatorio");
        }

        // Si no viene categoría, asignar "General"
        if (dto.getCategoriaTematica() == null || dto.getCategoriaTematica().trim().isEmpty()) {
            dto.setCategoriaTematica("General");
        }

        ArticuloEducacion articulo = modelMapper.map(dto, ArticuloEducacion.class);
        articulo.setFechaPublicacion(LocalDate.now());
        articulo = articuloEducacionRepository.save(articulo);
        return modelMapper.map(articulo, ArticuloEducacionDTO.class);
    }

    // ========== HU-36: Editar artículo educativo ==========

    public ArticuloEducacionDTO editarArticulo(Long id, ArticuloEducacionDTO dto) {
        ArticuloEducacion articulo = articuloEducacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artículo no encontrado"));

        // Actualizar solo los campos permitidos
        if (dto.getTitulo() != null && !dto.getTitulo().trim().isEmpty()) {
            articulo.setTitulo(dto.getTitulo());
        }
        if (dto.getDescripcionCorta() != null) {
            articulo.setDescripcionCorta(dto.getDescripcionCorta());
        }
        if (dto.getCuerpo() != null && !dto.getCuerpo().trim().isEmpty()) {
            articulo.setCuerpo(dto.getCuerpo());
        }
        if (dto.getCategoriaTematica() != null && !dto.getCategoriaTematica().trim().isEmpty()) {
            articulo.setCategoriaTematica(dto.getCategoriaTematica());
        }

        // NOTA: No se modifica la fecha de publicación

        articulo = articuloEducacionRepository.save(articulo);
        return modelMapper.map(articulo, ArticuloEducacionDTO.class);
    }

    // ========== HU-37: Eliminar artículo educativo ==========

    public ResponseEntity<?> eliminarArticulo(Long id) {
        if (!articuloEducacionRepository.existsById(id)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Artículo no encontrado"));
        }
        articuloEducacionRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Artículo eliminado correctamente"));
    }

    // ========== HU-39: Listar usuarios (Admin) ==========

    public List<AdminUsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<AdminUsuarioDTO> resultado = new ArrayList<>();

        for (Usuario u : usuarios) {
            AdminUsuarioDTO dto = new AdminUsuarioDTO();
            dto.setId(u.getId());
            dto.setNombreCompleto(u.getNombreCompleto());
            dto.setCorreo(u.getCorreo());
            dto.setRole(u.getRole());
            dto.setFechaRegistro(u.getFechaRegistro());
            dto.setTotalGastos(gastoRepository.findByUsuarioIdOrderByFechaGastoDesc(u.getId()).size());
            dto.setTotalIngresos(ingresoRepository.findByUsuarioIdOrderByFechaDesc(u.getId()).size());
            dto.setTotalDeudas(deudaRepository.findByUsuarioId(u.getId()).size());
            resultado.add(dto);
        }

        return resultado;
    }

    // ========== HU-39: Ver detalle de usuario (Admin) ==========

    public AdminUsuarioDetalleDTO getUsuarioDetalle(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        AdminUsuarioDetalleDTO dto = new AdminUsuarioDetalleDTO();
        dto.setId(usuario.getId());
        dto.setNombreCompleto(usuario.getNombreCompleto());
        dto.setCorreo(usuario.getCorreo());
        dto.setRole(usuario.getRole());
        dto.setFechaRegistro(usuario.getFechaRegistro());

        // Contar transacciones del usuario
        dto.setTotalGastos(gastoRepository.findByUsuarioIdOrderByFechaGastoDesc(usuario.getId()).size());
        dto.setTotalIngresos(ingresoRepository.findByUsuarioIdOrderByFechaDesc(usuario.getId()).size());
        dto.setTotalDeudas(deudaRepository.findByUsuarioId(usuario.getId()).size());

        // Contar metas y presupuestos
        dto.setTotalMetas(metaAhorroRepository.findByUsuarioId(usuario.getId()).size());

        // Para presupuestos, contamos todos (sin filtro de mes/año)
        // Usamos un método simple: findAll y filtramos por usuario
        dto.setTotalPresupuestos((int) presupuestoRepository.findAll().stream()
                .filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(usuario.getId()))
                .count());

        return dto;
    }
}
