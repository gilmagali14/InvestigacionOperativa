package com.operativa.gestion.dto;

import java.util.List;

public class DemandasDTO {
    private List<VentaPeriodoDTO> demandaHistorica;
    private String errorMetod;
    private double alfa;
    private int valorInicial;
    private PeriodosDTO cantidadPeriodos;

    public List<VentaPeriodoDTO> getDemandaHistorica() {
        return demandaHistorica;
    }

    public void setDemandaHistorica(List<VentaPeriodoDTO> demandaHistorica) {
        this.demandaHistorica = demandaHistorica;
    }

    public String getErrorMetod() {
        return errorMetod;
    }

    public void setErrorMetod(String errorMetod) {
        this.errorMetod = errorMetod;
    }

    public double getAlfa() {
        return alfa;
    }

    public void setAlfa(double alfa) {
        this.alfa = alfa;
    }

    public int getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(int valorInicial) {
        this.valorInicial = valorInicial;
    }

    public PeriodosDTO getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    public void setCantidadPeriodos(PeriodosDTO cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }
}
