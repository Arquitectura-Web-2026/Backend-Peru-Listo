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

public class PresupuestoDTO {

    private Long id;
    private Integer mes;
    private Integer anio;
    private BigDecimal montoLimite;
    private Long usuarioId;
    private Long categoriaId;

}
