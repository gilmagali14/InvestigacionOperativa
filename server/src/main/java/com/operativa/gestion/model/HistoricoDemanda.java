package com.operativa.gestion.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class HistoricoDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemanda;
    private Integer año;
    private Integer mes;
    private Integer cantidad ;

    @ManyToOne
    @JoinColumn(name = "idArticulo", nullable = false)
    private Articulo articulo;

    public Long getIdDemanda() {
        return idDemanda;
    }

    public void setIdDemanda(Long idDemanda) {
        this.idDemanda = idDemanda;
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

    public HistoricoDemanda() {
    }
}

