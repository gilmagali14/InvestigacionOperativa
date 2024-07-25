package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codVenta;

    private Double montoTotal;

    public Long getCodVenta() {return codVenta;}

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Venta() {

    }
}

