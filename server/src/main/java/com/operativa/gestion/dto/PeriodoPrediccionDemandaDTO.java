package com.operativa.gestion.dto;

public class PeriodoPrediccionDemandaDTO {
    Integer year;
    Integer month;
    Double prediction;
    Double error;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }
}
