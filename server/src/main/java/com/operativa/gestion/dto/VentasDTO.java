package com.operativa.gestion.dto;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.Venta;


public class VentasDTO {

    Venta venta;
    String fechaVenta;
    Long cantidadArticulos;
    Articulo articulo;

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

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Long getCantidadArticulos() {
        return cantidadArticulos;
    }

    public void setCantidadArticulos(Long cantidadArticulos) {
        this.cantidadArticulos = cantidadArticulos;
    }

    public VentasDTO(Venta venta, String fechaVenta, Long cantidadArticulos) {
        this.venta = venta;
        this.fechaVenta = fechaVenta;
        this.cantidadArticulos = cantidadArticulos;
    }

    public VentasDTO() {

    }
}
