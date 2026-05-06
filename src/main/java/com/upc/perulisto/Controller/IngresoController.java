package com.upc.perulisto.Controller;

import com.upc.perulisto.DTO.IngresoDTO;
import com.upc.perulisto.services.IngresoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


    @RestController
    @RequestMapping("/API")


    public class IngresoController{
        @Autowired
        private IngresoService ingresoService;

        @PostMapping("/registrar_ingreso")
        public IngresoDTO registrarIngreso(@RequestBody IngresoDTO ingresoDTO) {
            return ingresoService.registrarIngreso(ingresoDTO);
        }

        @GetMapping("/listar_ingresos")
        public List<IngresoDTO> listarIngresos() {
            return ingresoService.listarIngresos();
        }

        @PutMapping("/editar_ingreso/{id}")
        public IngresoDTO editarIngreso(@PathVariable Long id, @RequestBody IngresoDTO ingresoDTO) {
            return ingresoService.editarIngreso(id, ingresoDTO);
        }

        @DeleteMapping("/eliminar_ingreso/{id}")
        public void eliminarIngreso(@PathVariable Long id) {
            ingresoService.eliminarIngreso(id);
        }
}


