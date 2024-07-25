package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ArticuloProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticuloProveedor;
    private Integer tiempoEntrega;
    private Double costoPedido;
    private String modelo;
    private Double loteOptimo;
    private Double puntoPedido;
    private Double stockSeguridad;
    private Double cgi;

    @ManyToOne
    @JoinColumn(name = "idArticulo", nullable = false)
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "idProveedor", nullable = false)
    private Proveedor proveedor;

    @OneToMany(mappedBy = "articuloProveedor", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;

    public Long getIdArticuloProveedor() {
        return idArticuloProveedor;
    }

    public void setIdArticuloProveedor(Long idArticuloProveedor) {
        this.idArticuloProveedor = idArticuloProveedor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getLoteOptimo() {
        return loteOptimo;
    }

    public void setLoteOptimo(Double loteOptimo) {
        this.loteOptimo = loteOptimo;
    }

    public Double getPuntoPedido() {
        return puntoPedido;
    }

    public void setPuntoPedido(Double puntoPedido) {
        this.puntoPedido = puntoPedido;
    }

    public Double getStockSeguridad() {
        return stockSeguridad;
    }

    public void setStockSeguridad(Double stockSeguridad) {
        this.stockSeguridad = stockSeguridad;
    }

    public ArticuloProveedor(Long idArticuloProveedor, Integer tiempoEntrega, Double costoPedido, String modelo, Double loteOptimo,
                             Double puntoPedido, Double stockSeguridad, Articulo articulo, Proveedor proveedor) {
        this.idArticuloProveedor = idArticuloProveedor;
        this.tiempoEntrega = tiempoEntrega;
        this.costoPedido = costoPedido;
        this.modelo = modelo;
        this.loteOptimo = loteOptimo;
        this.puntoPedido = puntoPedido;
        this.stockSeguridad = stockSeguridad;
        this.articulo = articulo;
        this.proveedor = proveedor;
    }

    public ArticuloProveedor() {

    }

    public Long getId() {
        return idArticuloProveedor;
    }

    public void setId(Long id) {
        this.idArticuloProveedor = id;
    }

    public Integer getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(Integer tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public Double getCostoPedido() {
        return costoPedido;
    }

    public Double getCgi() {
        return cgi;
    }

    public void setCgi(Double cgi) {
        this.cgi = cgi;
    }

    public void setCostoPedido(Double costoPedido) {
        this.costoPedido = costoPedido;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
