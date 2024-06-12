package com.operativa.gestion.dto;

import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.TipoArticulo;
import jakarta.annotation.Nonnull;

public class ArticuloDTO {

    private String nombre;

    private String descripcion;

    @Nonnull
    private Long numeroLote;

    @Nonnull
    private TipoArticulo tipoArticulo;

    @Nonnull
    private Proveedor proveedor;

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    public Long getNumeroLote() {
        return numeroLote;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
}
