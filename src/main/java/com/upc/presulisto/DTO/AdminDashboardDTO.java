package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardDTO {

    private long totalUsuarios;
    private long usuariosNuevosEsteMes;
    private long totalTransacciones;
    private double totalGastosSistema;
    private double totalIngresosSistema;
    private long totalDeudasPendientes;
    private long totalMetasAhorro;

}
