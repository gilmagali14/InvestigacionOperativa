package com.operativa.gestion.dto;

import java.util.List;

public class PromedioMovilPExpoDTO {

    private List<VentaPeriodoDTO> demandaHistorica;
    private String errorMetod;
    private double alfa;
    private int valorInicial;

    public PromedioMovilPExpoDTO(List<VentaPeriodoDTO> demandaHistorica, String errorMetod, double alfa, int valorInicial) {
        this.demandaHistorica = demandaHistorica;
        this.errorMetod = errorMetod;
        this.alfa = alfa;
        this.valorInicial = valorInicial;
    }

    public int getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(int valorInicial) {
        this.valorInicial = valorInicial;
    }

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
}
