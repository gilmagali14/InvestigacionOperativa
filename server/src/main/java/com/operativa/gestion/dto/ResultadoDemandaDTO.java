package com.operativa.gestion.dto;

import java.util.List;

public class ResultadoDemandaDTO {

    private List<PrediccionDTO> prediccion;
    private int proximoPeriodo;
    private double error;

    public ResultadoDemandaDTO() {
    }

    public ResultadoDemandaDTO(List<PrediccionDTO> prediccion, int proximoPeriodo, double error) {
        this.prediccion = prediccion;
        this.proximoPeriodo = proximoPeriodo;
        this.error = error;
    }

    public List<PrediccionDTO> getPrediccion() {
        return prediccion;
    }

    public void setPrediccion(List<PrediccionDTO> prediccion) {
        this.prediccion = prediccion;
    }

    public int getProximoPeriodo() {
        return proximoPeriodo;
    }

    public void setProximoPeriodo(int proximoPeriodo) {
        this.proximoPeriodo = proximoPeriodo;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }
}
