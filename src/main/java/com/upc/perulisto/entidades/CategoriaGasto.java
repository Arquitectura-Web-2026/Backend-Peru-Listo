package com.upc.perulisto.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class CategoriaGasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String tipo = "gasto";
    private boolean esPredeterminada = false;
    private String colorHex;

    // Relación con Usuario (quién creó la categoría)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //  Una categoría tiene muchos gastos
    @OneToMany(mappedBy = "categoria")
    private List<Gasto> gastos;



}
