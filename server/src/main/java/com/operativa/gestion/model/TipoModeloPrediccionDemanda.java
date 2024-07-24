package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
public class TipoModeloPrediccionDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipoModeloPrediccionDemandaId;

    private String tipoModeloPrediccionDemandaNombre;

    private Boolean isDeleted;

    public Long getTipoModeloPrediccionDemandaId() {
        return tipoModeloPrediccionDemandaId;
    }

    public void setTipoModeloPrediccionDemandaId(Long tipoModeloPrediccionDemandaId) {
        this.tipoModeloPrediccionDemandaId = tipoModeloPrediccionDemandaId;
    }

    public String getTipoModeloPrediccionDemandaNombre() {
        return tipoModeloPrediccionDemandaNombre;
    }

    public void setTipoModeloPrediccionDemandaNombre(String tipoModeloPrediccionDemandaNombre) {
        this.tipoModeloPrediccionDemandaNombre = tipoModeloPrediccionDemandaNombre;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
