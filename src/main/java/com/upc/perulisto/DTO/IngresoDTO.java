package com.upc.perulisto.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class IngresoDTO {

    private Long id;
    private String descripcion;
    private double monto;
    private LocalDate fecha;

    private Long usuarioId;
}
