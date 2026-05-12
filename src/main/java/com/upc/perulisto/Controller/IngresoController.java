package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.IngresoDTO;
import com.upc.perulisto.services.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/API")


    public class IngresoController{
        @Autowired
        private IngresoService ingresoService;

        // HU-07: Registro de ingreso
        @PostMapping("/registrar_ingreso")
        public IngresoDTO registrarIngreso(@RequestBody IngresoDTO ingresoDTO) {
            return ingresoService.registrarIngreso(ingresoDTO);
        }

        // HU-09: Historial y filtros de ingresos por mes/año
        @GetMapping("/listar_ingresos")
        public List<IngresoDTO> listarIngresos(@RequestParam Long usuarioId,
                                               @RequestParam(required = false) Integer mes,
                                               @RequestParam(required = false) Integer anio) {
            if (mes != null && anio != null) {
                return ingresoService.listarIngresosPorMesYAnio(usuarioId, mes, anio);
            }
            return ingresoService.listarIngresosPorUsuario(usuarioId);
        }

        // HU-13: Búsqueda de ingresos
        @GetMapping("/buscar_ingresos")
        public List<IngresoDTO> buscarIngresos(@RequestParam Long usuarioId,
                                                @RequestParam String q,
                                                @RequestParam(required = false) Integer mes,
                                                @RequestParam(required = false) Integer anio) {
            if (q.length() < 3) {
                throw new RuntimeException("El término de búsqueda debe tener al menos 3 caracteres");
            }
            if (mes != null && anio != null) {
                return ingresoService.buscarIngresosPorMesYAnio(usuarioId, q, mes, anio);
            }
            return ingresoService.buscarIngresosPorUsuario(usuarioId, q);
        }

        // HU-10: Edición de ingreso
        @PutMapping("/editar_ingreso/{id}")
        public IngresoDTO editarIngreso(@PathVariable Long id, @RequestBody IngresoDTO ingresoDTO) {
            return ingresoService.editarIngreso(id, ingresoDTO);
        }

        // HU-11: Eliminación de ingreso
        @DeleteMapping("/eliminar_ingreso/{id}")
        public ResponseEntity<?> eliminarIngreso(@PathVariable Long id) {
            return ingresoService.eliminarIngreso(id);
        }
}


