package com.operativa.gestion.dto;

public class ProximoPeriodoDemandaDTO {

    Integer month;
    Integer year;
    Integer quantity;
    Long model;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getModel() {
        return model;
    }

    public void setModel(Long model) {
        this.model = model;
    }
}

