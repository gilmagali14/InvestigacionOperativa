package com.operativa.gestion.dto;

import java.util.List;

public class VentaDTO {

    private List<ArticuloVentaDTO> articulos;

    private String fechaVenta;

    public String getFechaVenta() {
        return fechaVenta;
    }

    public List<ArticuloVentaDTO> getArticulos() {
        return articulos;
    }
}
