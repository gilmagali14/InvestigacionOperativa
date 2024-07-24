package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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

    private String prediccionDemandaColor;

    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "tipoModeloPrediccionDemandaId", nullable = false)
    private TipoModeloPrediccionDemanda tipoModeloPrediccionDemanda;

    public Long getIdPrediccionDemanda() {
        return idPrediccionDemanda;
    }

    public void setIdPrediccionDemanda(Long idPrediccionDemanda) {
        this.idPrediccionDemanda = idPrediccionDemanda;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public String getPrediccionDemandaColor() {
        return prediccionDemandaColor;
    }

    public void setPrediccionDemandaColor(String prediccionDemandaColor) {
        this.prediccionDemandaColor = prediccionDemandaColor;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public TipoModeloPrediccionDemanda getTipoModeloPrediccionDemanda() {
        return tipoModeloPrediccionDemanda;
    }

    public void setTipoModeloPrediccionDemanda(TipoModeloPrediccionDemanda tipoModeloPrediccionDemanda) {
        this.tipoModeloPrediccionDemanda = tipoModeloPrediccionDemanda;
    }
}
