package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
public class ArticuloVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codArticuloVenta;
    private int cantidadArticulos;
    @ManyToOne
    @JoinColumn(name = "articulo_id")
    private Articulo articulo;
    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
    private int ano;
    private int mes;
    private int dia;

    public ArticuloVenta() {

    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Long getCodArticulo() {
        return codArticuloVenta;
    }

    public void setCodArticulo(Long codArticulo) {
        this.codArticuloVenta = codArticulo;
    }

    public int getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(int cantidadArticulos) {
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
}
