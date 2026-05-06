package com.upc.perulisto.entiidades;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor


public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double monto;
    private LocalDate fechaGasto;
    private String descripcion;
    private LocalTime fechaCreacion;

    //  Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // 🔗 Relación con CategoríaGasto
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaGasto categoria;



}
