package com.operativa.gestion.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PrediccionDemandaDTO {

    Long id;
    String type;
    String color;
    Integer num;
    List<PeriodoPrediccionDemandaDTO> periods = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<PeriodoPrediccionDemandaDTO> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PeriodoPrediccionDemandaDTO> periods) {
        this.periods = periods;
    }
}

