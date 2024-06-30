package com.operativa.gestion.dto;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public class ArticuloDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String nombreTipoArticulo;

    private String nombreProveedor;

    private BigDecimal precio;

    private BigDecimal costoAlmacenamiento;

    private Long stock;

    private Long stockSeguridad;

    private String modelo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Long getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(Long stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public BigDecimal getCostoAlmacenamiento() {
        return costoAlmacenamiento;
    }

    public void setCostoAlmacenamiento(BigDecimal costoAlmacenamiento) {
        this.costoAlmacenamiento = costoAlmacenamiento;
    }

    @Nonnull
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(@Nonnull BigDecimal precio) {
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Nonnull
    public String getNombreTipoArticulo() {
        return nombreTipoArticulo;
    }

    public void setNombreTipoArticulo(@Nonnull String nombreTipoArticulo) {
        this.nombreTipoArticulo = nombreTipoArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }


    @Nonnull
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(@Nonnull String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public ArticuloDTO(@Nonnull BigDecimal precio, @Nonnull String nombreProveedor, @Nonnull String nombreTipoArticulo,
                       String descripcion, String nombre) {
        this.precio = precio;
        this.nombreProveedor = nombreProveedor;
        this.nombreTipoArticulo = nombreTipoArticulo;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }
}
