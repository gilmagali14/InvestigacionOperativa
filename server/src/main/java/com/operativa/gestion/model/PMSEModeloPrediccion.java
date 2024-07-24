package com.operativa.gestion.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PMSE")
public class PMSEModeloPrediccion extends PrediccionDemanda {
    private Double alpha;
    private Double root;

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
}
