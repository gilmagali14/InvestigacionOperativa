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

    @OneToMany(mappedBy = "inventario")
    private List<Articulo> articulos;

    public Inventario() {

    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
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

    public Inventario(Long id, Long stockSeguridad, Long stock, String modelo, List<Articulo> articulos) {
        this.id = id;
        this.stockSeguridad = stockSeguridad;
        this.stock = stock;
        this.modelo = modelo;
        this.articulos = articulos;
    }
}
