package com.operativa.gestion.dto;

import java.util.List;

public class ArticulosProveedoresDTO {

    private Long idArticulo;
    private String nombreArticulo;
    private String descripcion;
    private Double precio;
    private int stock;
    private String tipoArticulo;
    private List<ArticuloProveedorDTO> articuloProveedor;

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public List<ArticuloProveedorDTO> getArticuloProveedor() {
        return articuloProveedor;
    }

    public void setArticuloProveedor(List<ArticuloProveedorDTO> articuloProveedor) {
        this.articuloProveedor = articuloProveedor;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ArticulosProveedoresDTO() {
    }
}
