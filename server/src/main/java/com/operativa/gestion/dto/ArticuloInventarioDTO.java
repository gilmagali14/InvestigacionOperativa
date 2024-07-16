package com.operativa.gestion.dto;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.Inventario;

public class ArticuloInventarioDTO {

    private Articulo articulo;
    private Inventario inventario;

    public Inventario getInventario() {
        return inventario;
    }

    public Articulo getArticulo() {
        return articulo;
    }
}
