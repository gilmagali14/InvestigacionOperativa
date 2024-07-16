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

    private Long loteOptimo;

    private Long puntoPedido;

    public Long getLoteOptimo() {
        return loteOptimo;
    }

    public void setLoteOptimo(Long loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public Long getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(Long puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    @OneToMany(mappedBy = "inventario")
    private List<Articulo> articulos;

    public Inventario() {
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

    public Inventario(Long id, Long stockSeguridad, Long stock, String modelo, Long loteOptimo, Long puntoPedido,
                      List<Articulo> articulos) {
        this.id = id;
        this.stockSeguridad = stockSeguridad;
        this.stock = stock;
        this.modelo = modelo;
        this.loteOptimo = loteOptimo;
        this.puntoPedido = puntoPedido;
        this.articulos = articulos;
    }
}
