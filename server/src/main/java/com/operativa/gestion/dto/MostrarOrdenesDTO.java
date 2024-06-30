package com.operativa.gestion.dto;

import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.model.OrdenDeCompra;

import java.util.ArrayList;
import java.util.List;

public class MostrarOrdenesDTO {

    List<OrdenDeCompra> ordenDeCompras;
    List<OrdenCompraDetalle> ordenCompraDetalles;

    public List<OrdenDeCompra> getOrdenDeCompras() {
        return ordenDeCompras;
    }

    public void setOrdenDeCompras(List<OrdenDeCompra> ordenDeCompras) {
        this.ordenDeCompras = ordenDeCompras;
    }

    public List<OrdenCompraDetalle> getOrdenCompraDetalles() {
        return ordenCompraDetalles;
    }

    public void setOrdenCompraDetalles(List<OrdenCompraDetalle> ordenCompraDetalles) {
        this.ordenCompraDetalles = ordenCompraDetalles;
    }

    public MostrarOrdenesDTO(List<OrdenDeCompra> ordenDeCompras, List<OrdenCompraDetalle> ordenCompraDetalles) {
        this.ordenDeCompras = ordenDeCompras;
        this.ordenCompraDetalles = ordenCompraDetalles;
    }
}
