package com.operativa.gestion.dto;

public class ParametrosDemandaDTO {

    Integer periodosAPredecir;
    String metodoCalculoError;
    Double errorAceptable;

    public Integer getPeriodosAPredecir() {
        return periodosAPredecir;
    }

    public String getMetodoCalculoError() {
        return metodoCalculoError;
    }

    public Double getErrorAceptable() {
        return errorAceptable;
    }

    public void setPeriodosAPredecir(Integer periodosAPredecir) {
        this.periodosAPredecir = periodosAPredecir;
    }

    public void setMetodoCalculoError(String metodoCalculoError) {
        this.metodoCalculoError = metodoCalculoError;
    }

    public void setErrorAceptable(Double errorAceptable) {
        this.errorAceptable = errorAceptable;
    }
}
