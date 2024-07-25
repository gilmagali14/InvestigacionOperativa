package com.operativa.gestion.dto;

import java.util.List;

public class RegresionLinealDTO {
    private List<VentaPeriodoDTO> demandaHistorica;
    private String errorMetod;

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

    public RegresionLinealDTO(List<VentaPeriodoDTO> demandaHistorica, String errorMetod) {
        this.demandaHistorica = demandaHistorica;
        this.errorMetod = errorMetod;
    }
}
