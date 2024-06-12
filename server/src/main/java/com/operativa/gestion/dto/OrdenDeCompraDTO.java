package com.operativa.gestion.dto;

import com.operativa.gestion.model.OrdenDeCompra;

import java.util.List;
import java.util.Map;

public class OrdenDeCompraDTO {

    private OrdenDeCompra ordenDeCompra;
    private List<ArticuloOrdenCompraDTO> articulos;

    public List<ArticuloOrdenCompraDTO> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ArticuloOrdenCompraDTO> articulos) {
        this.articulos = articulos;
    }

    public void setOrdenDeCompra(OrdenDeCompra ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    public OrdenDeCompra getOrdenDeCompra() {
        return ordenDeCompra;
    }
}
