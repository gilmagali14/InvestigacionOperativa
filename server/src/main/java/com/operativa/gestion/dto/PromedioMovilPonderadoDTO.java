package com.operativa.gestion.dto;

import java.util.List;

public class PromedioMovilPonderadoDTO {
    private List<VentaPeriodoDTO> demandaHistorica;
    private PeriodosDTO cantidadPeriodos;
    private String errorMetod;

    public List<VentaPeriodoDTO> getDemandaHistorica() {
        return demandaHistorica;
    }

    public void setDemandaHistorica(List<VentaPeriodoDTO> demandaHistorica) {
        this.demandaHistorica = demandaHistorica;
    }

    public PeriodosDTO getCantidadPeriodos() {
        return cantidadPeriodos;
    }

    public void setCantidadPeriodos(PeriodosDTO cantidadPeriodos) {
        this.cantidadPeriodos = cantidadPeriodos;
    }

    public String getErrorMetod() {
        return errorMetod;
    }

    public void setErrorMetod(String errorMetod) {
        this.errorMetod = errorMetod;
    }

    public PromedioMovilPonderadoDTO(List<VentaPeriodoDTO> demandaHistorica, PeriodosDTO cantidadPeriodos, String errorMetod) {
        this.demandaHistorica = demandaHistorica;
        this.cantidadPeriodos = cantidadPeriodos;
        this.errorMetod = errorMetod;
    }
}
