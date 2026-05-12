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
public class ArticuloEducacionDTO {
    private Long id;
    private String titulo;
    private String descripcionCorta;
    private String cuerpo;
    private String categoriaTematica;
    private LocalDate fechaPublicacion;
}
