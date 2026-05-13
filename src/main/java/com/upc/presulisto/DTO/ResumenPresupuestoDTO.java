package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenPresupuestoDTO {
    private String categoria;
    private BigDecimal montoLimite;
    private BigDecimal montoGastado;
    private double porcentajeUso;
}
