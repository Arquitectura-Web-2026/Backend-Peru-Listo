package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUsuarioDTO {

    private Long id;
    private String nombreCompleto;
    private String correo;
    private String role;
    private LocalDate fechaRegistro;
    private int totalGastos;
    private int totalIngresos;
    private int totalDeudas;

}
