package com.operativa.gestion.dto;

import java.util.List;

public class ResultadosDemandaDTO {

    List<PeriodoDemandaRealDTO> periodos;

    List<PrediccionDemandaDTO> predicciones;

    ProximoPeriodoDemandaDTO proximoPeriodoDemanda;

    Double errorAceptable;

    public List<PeriodoDemandaRealDTO> getPeriodos() {
        return periodos;
    }

    public void setPeriodos(List<PeriodoDemandaRealDTO> periodos) {
        this.periodos = periodos;
    }

    public List<PrediccionDemandaDTO> getPredicciones() {
        return predicciones;
    }

    public void setPredicciones(List<PrediccionDemandaDTO> predicciones) {
        this.predicciones = predicciones;
    }

    public ProximoPeriodoDemandaDTO getProximoPeriodoDemanda() {
        return proximoPeriodoDemanda;
    }

    public void setProximoPeriodoDemanda(ProximoPeriodoDemandaDTO proximoPeriodoDemanda) {
        this.proximoPeriodoDemanda = proximoPeriodoDemanda;
    }

    public Double getErrorAceptable() {
        return errorAceptable;
    }

    public void setErrorAceptable(Double errorAceptable) {
        this.errorAceptable = errorAceptable;
    }
}


