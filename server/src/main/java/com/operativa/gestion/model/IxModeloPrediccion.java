package com.operativa.gestion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Ix")
public class IxModeloPrediccion extends PrediccionDemanda {

    private Integer length;
    private Integer expectedDemand;

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
