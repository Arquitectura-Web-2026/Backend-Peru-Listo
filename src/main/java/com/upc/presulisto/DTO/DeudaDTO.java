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


public class DeudaDTO {
    private Long id;
    private String acreedor;
    private double monto;
    private LocalDate fechaLimite;
    private String estado;

    private Long usuarioId;
}
