package com.operativa.gestion.dto;

public class PrediccionDemandaModeloDTO {

    Long id;
    String type;
    String color;
    Integer num;
    //PMP
    String ponderations;
    //PMSE
    Double alpha;
    Double root;
    //RL
    Integer ignorePeriods;
    //Ix
    Integer length;
    Integer expectedDemand;

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

    public String getPonderations() {
        return ponderations;
    }

    public void setPonderations(String ponderations) {
        this.ponderations = ponderations;
    }

    public Double getAlpha() {
        return alpha;
    }

    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public Double getRoot() {
        return root;
    }

    public void setRoot(Double root) {
        this.root = root;
    }

    public Integer getIgnorePeriods() {
        return ignorePeriods;
    }

    public void setIgnorePeriods(Integer ignorePeriods) {
        this.ignorePeriods = ignorePeriods;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getExpectedDemand() {
        return expectedDemand;
    }

    public void setExpectedDemand(Integer expectedDemand) {
        this.expectedDemand = expectedDemand;
    }
}
