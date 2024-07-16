package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long codVenta;

    private BigDecimal montoTotal;

    public Long getCodVenta() {return codVenta;}

    public void setCodVenta(Long codVenta) {this.codVenta = codVenta;}

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta() {

    }
}

