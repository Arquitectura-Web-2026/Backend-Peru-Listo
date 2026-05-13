package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class MetaAhorroDTO {

    private Long id;
    private String nombre;
    private BigDecimal montoObjetivo;
    private BigDecimal montoActual;
    private LocalDate fechaLimite;
    private String estado;
    private LocalDateTime fechaCreacion;
    private Long usuarioId;

}
