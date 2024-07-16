package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long stockSeguridad;

    private Long stock;

    private String modelo;

    private Double loteOptimo;

    private Double puntoPedido;

    public Double getLoteOptimo() {
        return loteOptimo;
    }

    public void setLoteOptimo(Double loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public Double getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(Double puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public Inventario() {
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

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

    public Inventario(Long id, Long stockSeguridad, Long stock, String modelo, Double loteOptimo, Double puntoPedido,
                      List<Articulo> articulos) {
        this.id = id;
        this.stockSeguridad = stockSeguridad;
        this.stock = stock;
        this.modelo = modelo;
        this.loteOptimo = loteOptimo;
        this.puntoPedido = puntoPedido;
    }
}
