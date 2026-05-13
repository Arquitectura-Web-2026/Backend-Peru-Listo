package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResumenDTO {
    private double totalIngresos;
    private double totalGastos;
    private double balance;
    private int mes;
    private int anio;
}
