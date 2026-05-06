package com.upc.perulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class GastoDTO {

    private Long id;
    private String descripcion;
    private double monto;
    private LocalDate fechagasto;
    private LocalTime fechacreacion;


    private Long usuarioId;
    private Long categoriaId;


}

