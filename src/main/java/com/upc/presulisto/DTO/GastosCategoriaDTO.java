package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GastosCategoriaDTO {
    private String categoriaNombre;
    private double monto;
    private double porcentaje;
}
