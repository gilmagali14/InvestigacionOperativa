package com.operativa.gestion.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity

public class Demanda {
    @Id
    @GeneratedValue
    private Long idDemanda;

    @CreationTimestamp
    private LocalDateTime fechaInicioPeriodo;
    @CreationTimestamp
    private LocalDateTime fechaFinPeriodo;

    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "codArticulo")
    private Articulo articulo;

    public Demanda(LocalDateTime fechaInicioPeriodo, LocalDateTime fechaFinPeriodo, int cantidad, Articulo articulo) {
        this.fechaInicioPeriodo = fechaInicioPeriodo;
        this.fechaFinPeriodo = fechaFinPeriodo;
        this.cantidad = cantidad;
        this.articulo = articulo;
    }
}

