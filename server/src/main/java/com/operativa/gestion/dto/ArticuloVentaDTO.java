package com.operativa.gestion.dto;

public class ArticuloVentaDTO {

    private Long idArticuloVenta;
    private Long cantidadArticulo;

    public Long getIdArticuloVenta() {
        return idArticuloVenta;
    }

    public void setIdArticuloVenta(Long idArticuloVenta) {
        this.idArticuloVenta = idArticuloVenta;
    }

    public Long getCantidadArticulo() {
        return cantidadArticulo;
    }

    public void setCantidadArticulo(Long cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }
}
