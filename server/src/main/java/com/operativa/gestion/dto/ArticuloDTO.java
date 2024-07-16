package com.operativa.gestion.dto;

public class ArticuloDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String nombreTipoArticulo;
    private Double precio;
    private Integer tasaRotacion;
    private int stock;

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreTipoArticulo() {
        return nombreTipoArticulo;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getTasaRotacion() {
        return tasaRotacion;
    }

    public int getStock() {
        return stock;
    }
}
