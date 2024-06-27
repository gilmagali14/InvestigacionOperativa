package com.operativa.gestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Venta {

    @Id
    @GeneratedValue
    private Long codVenta;

    private LocalDateTime fechaVenta;

    private double montoTotal;

    public Long getCodVenta() {return codVenta;}

    public void setCodVenta(Long codVenta) {this.codVenta = codVenta;}

    public LocalDateTime getFechaVenta() {return fechaVenta;}

    public void setFechaVenta(LocalDateTime fechaVenta) {this.fechaVenta = fechaVenta;}

    public Venta (Long codVenta, LocalDateTime fechaVenta) {
        this.codVenta = codVenta;
        this.fechaVenta = fechaVenta;
    }

    public Venta() {

    }
}

