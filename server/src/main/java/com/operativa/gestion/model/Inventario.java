package com.operativa.gestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Inventario {

    @Id
    @GeneratedValue
    private Long id;

    private Long idArticulo;

    private Long stockSeguridad;

    private Long stock;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(Long stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }
}
