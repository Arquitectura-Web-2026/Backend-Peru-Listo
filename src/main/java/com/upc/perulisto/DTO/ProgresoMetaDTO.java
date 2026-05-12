package com.upc.perulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProgresoMetaDTO {
    private Long metaId;
    private String nombre;
    private BigDecimal montoObjetivo;
    private BigDecimal montoActual;
    private double porcentaje;
    private String estado;
}
