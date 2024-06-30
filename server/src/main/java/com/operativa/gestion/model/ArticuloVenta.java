package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ArticuloVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codArticuloVenta;

    private Long cantidadArticulos;

    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;

    String fechaVenta;

    public ArticuloVenta() {

    }

    public Long getCodArticulo() {
        return codArticuloVenta;
    }

    public void setCodArticulo(Long codArticulo) {
        this.codArticuloVenta = codArticulo;
    }

    public Long getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(Long cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public ArticuloVenta(Articulo articulo, Long cantidadArticulos) {
        this.articulo = articulo;
        this.cantidadArticulos = cantidadArticulos;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public ArticuloVenta(Articulo articulo, Venta venta, Long cantidadArticulos, String fechaVenta) {
        this.articulo = articulo;
        this.venta = venta;
        this.cantidadArticulos = cantidadArticulos;
        this.fechaVenta = fechaVenta;
    }
}
