package com.operativa.gestion.model;
import jakarta.persistence.*;

@Entity
public class Demanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemanda;

    private String mesPeriodoInicio;

    private String mesPeriodoFin;

    private int cantidad;

    private int pronostico;

    private Long idArticulo;

    private String metodo;

    public Long getIdDemanda() {
        return idDemanda;
    }

    public void setIdDemanda(Long idDemanda) {
        this.idDemanda = idDemanda;
    }

    public String getMesPeriodoInicio() {
        return mesPeriodoInicio;
    }

    public void setMesPeriodoInicio(String mesPeriodoInicio) {
        this.mesPeriodoInicio = mesPeriodoInicio;
    }

    public String getMesPeriodoFin() {
        return mesPeriodoFin;
    }

    public void setMesPeriodoFin(String mesPeriodoFin) {
        this.mesPeriodoFin = mesPeriodoFin;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPronostico() {
        return pronostico;
    }

    public void setPronostico(int pronostico) {
        this.pronostico = pronostico;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Demanda(String mesPeriodoInicio, String mesPeriodoFin, int cantidad, int pronostico, Long idArticulo, String metodo) {
        this.mesPeriodoInicio = mesPeriodoInicio;
        this.mesPeriodoFin = mesPeriodoFin;
        this.cantidad = cantidad;
        this.pronostico = pronostico;
        this.idArticulo = idArticulo;
        this.metodo = metodo;
    }

    public Demanda() {
    }
}

