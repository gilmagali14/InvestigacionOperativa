package com.operativa.gestion.dto;

public class ArticuloProveedorDTO {

    private int tiempoEntrega;
    private Double costoPedido;
    private String proveedor;
    private String modelo;
    private Double stockSeguridad;
    private Long articulo;
    private Double loteOptimo;
    private Double puntoPedido;
    private Double cgi;

    public Double getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(Double puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public void setArticulo(Long articulo) {
        this.articulo = articulo;
    }

    public Long getArticulo() {
        return articulo;
    }

    public Double getLoteOptimo() {
        return loteOptimo;
    }

    public void setLoteOptimo(Double loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public int getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public Double getCostoPedido() {
        return costoPedido;
    }

    public void setCostoPedido(Double costoPedido) {
        this.costoPedido = costoPedido;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(Double stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public Double getCgi() {
        return cgi;
    }

    public void setCgi(Double cgi) {
        this.cgi = cgi;
    }

    public ArticuloProveedorDTO(int tiempoEntrega, Double costoPedido, String proveedor, String modelo,
                                Double stockSeguridad, Double loteOptimo, Double puntoPedido, Double cgi) {
        this.tiempoEntrega = tiempoEntrega;
        this.costoPedido = costoPedido;
        this.proveedor = proveedor;
        this.modelo = modelo;
        this.stockSeguridad = stockSeguridad;
        this.loteOptimo = loteOptimo;
        this.puntoPedido = puntoPedido;
        this.cgi = cgi;
    }
}
