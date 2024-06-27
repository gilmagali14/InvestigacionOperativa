package com.operativa.gestion.dto;

import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

public class ArticuloDTO {

    private String nombre;

    private String descripcion;

    @Nonnull
    private String nombreTipoArticulo;

    @Nonnull
    private String nombreProveedor;

    @Nonnull
    private BigDecimal precio;

    private BigDecimal costoAlmacenamiento;

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
