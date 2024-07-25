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
    @JoinColumn(name = "idArticuloProveedor")
    private ArticuloProveedor articuloProveedor;

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

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }

    public ArticuloProveedor getArticuloProveedor() {
        return articuloProveedor;
    }

    public void setArticuloProveedor(ArticuloProveedor articuloProveedor) {
        this.articuloProveedor = articuloProveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenCompraDetalle(OrdenDeCompra ordenDeCompra, ArticuloProveedor articuloProveedor, int cantidad) {
        this.ordenDeCompra = ordenDeCompra;
        this.articuloProveedor = articuloProveedor;
        this.cantidad = cantidad;
    }
}
