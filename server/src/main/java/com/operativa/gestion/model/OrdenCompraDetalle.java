package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
public class OrdenCompraDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private OrdenDeCompra ordenDeCompra;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    private int cantidad;

    public OrdenCompraDetalle() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public Articulo getArticulo() { return articulo; }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenCompraDetalle(OrdenDeCompra ordenDeCompra, Articulo articulo, int cantidad) {
        this.ordenDeCompra = ordenDeCompra;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }
}
