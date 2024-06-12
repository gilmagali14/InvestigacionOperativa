package com.operativa.gestion.dto;

public class ArticuloOrdenCompraDTO {

    private long idArticulo;
    private int cantidad;

    public long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
