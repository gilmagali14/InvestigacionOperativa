package com.operativa.gestion.dto;

import com.operativa.gestion.model.TipoArticulo;

public class ArticuloDTO {

    private String nombre;
    private String descripcion;
    private TipoArticulo tipoArticulo;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }
}
