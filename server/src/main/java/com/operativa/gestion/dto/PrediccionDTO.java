package com.operativa.gestion.dto;

public class PrediccionDTO {
    private String periodoInicio;
    private String periodoFin;
    private int valor;

    public PrediccionDTO() {
    }

    public PrediccionDTO(String periodoInicio, String periodoFin, int valor) {
        this.periodoInicio = periodoInicio;
        this.periodoFin = periodoFin;
        this.valor = valor;
    }

    public String getPeriodoInicio() {
        return periodoInicio;
    }

    public void setPeriodoInicio(String periodoInicio) {
        this.periodoInicio = periodoInicio;
    }

    public String getPeriodoFin() {
        return periodoFin;
    }

    public void setPeriodoFin(String periodoFin) {
        this.periodoFin = periodoFin;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
