package com.upc.presulisto.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloEducacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(length = 255)
    private String descripcionCorta;

    @Column(columnDefinition = "TEXT")
    private String cuerpo;

    private String categoriaTematica;

    private LocalDate fechaPublicacion;
}
