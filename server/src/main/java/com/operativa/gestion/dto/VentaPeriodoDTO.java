package com.operativa.gestion.dto;

import java.util.List;

public class VentaPeriodoDTO {
    private List<ArticuloVentaDTO> salesInPeriod;
    private int quantity;
    private String periodStart;
    private String periodEnd;


    public List<ArticuloVentaDTO> getSalesInPeriod() {
        return salesInPeriod;
    }

    public void setSalesInPeriod(List<ArticuloVentaDTO> salesInPeriod) {
        this.salesInPeriod = salesInPeriod;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }
}
