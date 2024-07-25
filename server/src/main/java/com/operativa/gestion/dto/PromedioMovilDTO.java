package com.operativa.gestion.dto;

import java.util.List;

public class PromedioMovilDTO {
    private List<VentaPeriodoDTO> demandaHistorica;
    private int cantidadPeriodos;
    private String errorMetod;

    public PromedioMovilDTO() {
    }

    public PromedioMovilDTO(List<VentaPeriodoDTO> demandaHistorica, int cantidadPeriodos, String errorMetod) {
        this.demandaHistorica = demandaHistorica;
        this.cantidadPeriodos = cantidadPeriodos;
        this.errorMetod = errorMetod;
    }

    public List<VentaPeriodoDTO> getDemandaHistorica() {
        return demandaHistorica;
    }

    public void setDemandaHistorica(List<VentaPeriodoDTO> demandaHistorica) {
        this.demandaHistorica = demandaHistorica;
    }

    public int getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    public void setCantidadPeriodos(int cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }

    public String getErrorMetod() {
        return errorMetod;
    }

    public void setErrorMetod(String errorMetod) {
        this.errorMetod = errorMetod;
    }

}
