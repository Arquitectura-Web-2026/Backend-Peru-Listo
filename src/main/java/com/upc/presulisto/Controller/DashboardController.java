package com.upc.presulisto.Controller;

import com.upc.presulisto.DTO.ComparativaMensualDTO;
import com.upc.presulisto.DTO.DashboardResumenDTO;
import com.upc.presulisto.DTO.GastosCategoriaDTO;
import com.upc.presulisto.DTO.ProgresoMetaDTO;
import com.upc.presulisto.DTO.ResumenPresupuestoDTO;
import com.upc.presulisto.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/API/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // HU-14: Tarjetas de resumen mensual
    @GetMapping("/resumen_mensual")
    public DashboardResumenDTO getResumenMensual(
            @RequestParam Long usuarioId,
            @RequestParam int mes,
            @RequestParam int anio) {
        return dashboardService.getResumenMensual(usuarioId, mes, anio);
    }

    // HU-15: Gráfico de gastos por categoría
    @GetMapping("/gastos_por_categoria")
    public List<GastosCategoriaDTO> getGastosPorCategoria(
            @RequestParam Long usuarioId,
            @RequestParam int mes,
            @RequestParam int anio) {
        return dashboardService.getGastosPorCategoria(usuarioId, mes, anio);
    }

    // HU-20: Comparativa ingresos vs gastos (mensual)
    @GetMapping("/comparativa_mensual")
    public List<ComparativaMensualDTO> getComparativaMensual(
            @RequestParam Long usuarioId,
            @RequestParam(defaultValue = "6") int meses) {
        return dashboardService.getComparativaMensual(usuarioId, meses);
    }

    // HU-31: Resumen de estado de presupuestos en dashboard
    @GetMapping("/resumen_presupuestos")
    public List<ResumenPresupuestoDTO> getResumenPresupuestos(
            @RequestParam Long usuarioId,
            @RequestParam int mes,
            @RequestParam int anio) {
        return dashboardService.getResumenPresupuestos(usuarioId, mes, anio);
    }

    // HU-32: Progreso de metas de ahorro en dashboard
    @GetMapping("/progreso_metas")
    public List<ProgresoMetaDTO> getProgresoMetas(@RequestParam Long usuarioId) {
        return dashboardService.getProgresoMetas(usuarioId);
    }
}
