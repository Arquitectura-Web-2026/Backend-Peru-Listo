package com.upc.perulisto.services;

import com.upc.perulisto.DTO.ComparativaMensualDTO;
import com.upc.perulisto.DTO.DashboardResumenDTO;
import com.upc.perulisto.DTO.GastosCategoriaDTO;
import com.upc.perulisto.DTO.ProgresoMetaDTO;
import com.upc.perulisto.DTO.ResumenPresupuestoDTO;
import com.upc.perulisto.entidades.Gasto;
import com.upc.perulisto.entidades.Ingreso;
import com.upc.perulisto.entidades.Presupuesto;
import com.upc.perulisto.entidades.MetaAhorro;
import com.upc.perulisto.repositorio.GastoRepository;
import com.upc.perulisto.repositorio.IngresoRepository;
import com.upc.perulisto.repositorio.PresupuestoRepository;
import com.upc.perulisto.repositorio.MetaAhorroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private GastoRepository gastoRepository;

    @Autowired
    private IngresoRepository ingresoRepository;

    @Autowired
    private PresupuestoRepository presupuestoRepository;

    @Autowired
    private MetaAhorroRepository metaAhorroRepository;

    // HU-14: Tarjetas de resumen mensual
    public DashboardResumenDTO getResumenMensual(Long usuarioId, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);

        List<Ingreso> ingresos = ingresoRepository.findByUsuarioIdAndFechaBetweenOrderByFechaDesc(
                usuarioId, fechaInicio, fechaFin);
        List<Gasto> gastos = gastoRepository.findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(
                usuarioId, fechaInicio, fechaFin);

        double totalIngresos = ingresos.stream()
                .mapToDouble(Ingreso::getMonto)
                .sum();

        double totalGastos = gastos.stream()
                .mapToDouble(Gasto::getMonto)
                .sum();

        double balance = totalIngresos - totalGastos;

        return new DashboardResumenDTO(totalIngresos, totalGastos, balance, mes, anio);
    }

    // HU-15: Gráfico de gastos por categoría
    public List<GastosCategoriaDTO> getGastosPorCategoria(Long usuarioId, int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);

        List<Gasto> gastos = gastoRepository.findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(
                usuarioId, fechaInicio, fechaFin);

        double totalGastos = gastos.stream()
                .mapToDouble(Gasto::getMonto)
                .sum();

        // Agrupar gastos por categoría
        Map<String, Double> gastosPorCategoria = gastos.stream()
                .collect(Collectors.groupingBy(
                        g -> g.getCategoria().getNombre(),
                        Collectors.summingDouble(Gasto::getMonto)
                ));

        List<GastosCategoriaDTO> resultado = new ArrayList<>();
        for (Map.Entry<String, Double> entry : gastosPorCategoria.entrySet()) {
            double porcentaje = totalGastos > 0 ? (entry.getValue() / totalGastos) * 100 : 0;
            resultado.add(new GastosCategoriaDTO(entry.getKey(), entry.getValue(), porcentaje));
        }

        return resultado;
    }

    // HU-20: Comparativa ingresos vs gastos (mensual)
    public List<ComparativaMensualDTO> getComparativaMensual(Long usuarioId, int meses) {
        List<ComparativaMensualDTO> resultado = new ArrayList<>();
        YearMonth current = YearMonth.now();

        for (int i = 0; i < meses; i++) {
            YearMonth ym = current.minusMonths(i);
            int mes = ym.getMonthValue();
            int anio = ym.getYear();

            LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
            LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);

            List<Ingreso> ingresos = ingresoRepository.findByUsuarioIdAndFechaBetweenOrderByFechaDesc(
                    usuarioId, fechaInicio, fechaFin);
            List<Gasto> gastos = gastoRepository.findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(
                    usuarioId, fechaInicio, fechaFin);

            double totalIngresos = ingresos.stream()
                    .mapToDouble(Ingreso::getMonto)
                    .sum();

            double totalGastos = gastos.stream()
                    .mapToDouble(Gasto::getMonto)
                    .sum();

            resultado.add(new ComparativaMensualDTO(mes, anio, totalIngresos, totalGastos));
        }

        return resultado;
    }

    // HU-31: Resumen de estado de presupuestos en dashboard
    public List<ResumenPresupuestoDTO> getResumenPresupuestos(Long usuarioId, int mes, int anio) {
        List<Presupuesto> presupuestos = presupuestoRepository.findByUsuarioIdAndMesAndAnio(
                usuarioId, mes, anio);

        List<ResumenPresupuestoDTO> resultado = new ArrayList<>();

        for (Presupuesto p : presupuestos) {
            LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
            LocalDate fechaFin = fechaInicio.plusMonths(1).minusDays(1);

            // Obtener gastos de esta categoría en el mes
            List<Gasto> gastosCategoria = gastoRepository
                    .findByUsuarioIdAndFechaGastoBetweenOrderByFechaGastoDesc(
                            usuarioId, fechaInicio, fechaFin)
                    .stream()
                    .filter(g -> g.getCategoria().getId() == p.getCategoria().getId())
                    .collect(Collectors.toList());

            BigDecimal montoGastado = gastosCategoria.stream()
                    .map(g -> BigDecimal.valueOf(g.getMonto()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            double porcentajeUso = p.getMontoLimite().doubleValue() > 0
                    ? (montoGastado.doubleValue() / p.getMontoLimite().doubleValue()) * 100
                    : 0;

            resultado.add(new ResumenPresupuestoDTO(
                    p.getCategoria().getNombre(),
                    p.getMontoLimite(),
                    montoGastado,
                    porcentajeUso
            ));
        }

        return resultado;
    }

    // HU-32: Progreso de metas de ahorro en dashboard
    public List<ProgresoMetaDTO> getProgresoMetas(Long usuarioId) {
        List<MetaAhorro> metas = metaAhorroRepository.findByUsuarioIdAndEstado(
                usuarioId, "en_progreso");

        List<ProgresoMetaDTO> resultado = new ArrayList<>();

        for (MetaAhorro meta : metas) {
            double porcentaje = meta.getMontoObjetivo().doubleValue() > 0
                    ? (meta.getMontoActual().doubleValue() / meta.getMontoObjetivo().doubleValue()) * 100
                    : 0;

            resultado.add(new ProgresoMetaDTO(
                    meta.getId(),
                    meta.getNombre(),
                    meta.getMontoObjetivo(),
                    meta.getMontoActual(),
                    porcentaje,
                    meta.getEstado()
            ));
        }

        return resultado;
    }
}
