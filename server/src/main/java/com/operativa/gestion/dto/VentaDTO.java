package com.operativa.gestion.dto;

public class VentaDTO {

    private Long idArticulo;
    private Integer cantidadArticulo;
    private String fecha;
    private String proveedor;

    public Long getIdArticulo() {
        return idArticulo;
    }

    public Integer getCantidadArticulo() {
        return cantidadArticulo;
    }

    public String getProveedor() {
        return proveedor;
    }
}
