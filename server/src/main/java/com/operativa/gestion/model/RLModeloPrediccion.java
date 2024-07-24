package com.operativa.gestion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("RL")
public class RLModeloPrediccion extends PrediccionDemanda {
    private Integer ignorePeriods;

    public Integer getIgnorePeriods() {
        return ignorePeriods;
    }

    public void setIgnorePeriods(Integer ignorePeriods) {
        this.ignorePeriods = ignorePeriods;
    }

}
