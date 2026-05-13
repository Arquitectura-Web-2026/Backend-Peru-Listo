package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComparativaMensualDTO {
    private int mes;
    private int anio;
    private double ingresos;
    private double gastos;
}
