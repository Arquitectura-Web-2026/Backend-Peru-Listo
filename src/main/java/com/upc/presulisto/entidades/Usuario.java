package com.upc.presulisto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    @Column(unique = true, nullable = false)
    private String correo;
    private String passwordHash;
    private String role = "USER";
    private LocalDate fechaRegistro;

    // Campos para recuperación de contraseña (HU-34)
    private String resetToken;
    private LocalDateTime resetTokenExpiry;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (role == null) {
            role = "USER";
        }
        if (fechaRegistro == null) {
            fechaRegistro = LocalDate.now();
        }
    }

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Gasto> gastos;

    // Relación con ingresos
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Ingreso> ingresos;

    // Relación con deudas
    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Deuda> deudas;



}
