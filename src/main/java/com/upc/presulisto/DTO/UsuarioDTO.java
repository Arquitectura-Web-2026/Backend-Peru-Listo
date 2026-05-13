package com.upc.presulisto.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class UsuarioDTO {

    private Long id;
    private String nombreCompleto;
    private String correo;
    private LocalDate fechaRegistro;
}
