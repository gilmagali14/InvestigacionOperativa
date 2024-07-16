package com.operativa.gestion.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
public class PrediccionDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPrediccionDemanda;
    private Integer año;
    private Integer mes;
    private Integer cantidad;
    private String metodo;

    @ManyToOne
    @JoinColumn(name = "idArticulo", nullable = false)
    private Articulo articulo;
}
