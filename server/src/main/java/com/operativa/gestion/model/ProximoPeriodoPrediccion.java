package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
public class ProximoPeriodoPrediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proximoPeriodoPrediccionId;
    private Integer año;
    private Integer mes;
    @ManyToOne
    @JoinColumn(name = "idArticulo", nullable = false)
    private Articulo articulo;
    private Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "idArticulo", nullable = false)
    private PrediccionDemanda prediccionDemanda;

    public Long getProximoPeriodoPrediccionId() {
        return proximoPeriodoPrediccionId;
    }

    public void setProximoPeriodoPrediccionId(Long proximoPeriodoPrediccionId) {
        this.proximoPeriodoPrediccionId = proximoPeriodoPrediccionId;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public PrediccionDemanda getPrediccionDemanda() {
        return prediccionDemanda;
    }

    public void setPrediccionDemanda(PrediccionDemanda prediccionDemanda) {
        this.prediccionDemanda = prediccionDemanda;
    }
}