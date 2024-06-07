package com.operativa.gestion.dto;

import com.operativa.gestion.model.OrdenDeCompra;

import java.util.List;

public class OrdenDeCompraDTO {
    private OrdenDeCompra ordenDeCompra;
    private List<Long> articulos;

    public List<Long> getArticulos() {
        return articulos;
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }
}
