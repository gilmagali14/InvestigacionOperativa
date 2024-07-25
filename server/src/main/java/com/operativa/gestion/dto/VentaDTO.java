package com.operativa.gestion.dto;

public class VentaDTO {

    private Long idArticulo;
    private Integer cantidadArticulo;
    private int año;
    private int mes;
    private int dia;
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

    public int getAño() {
        return año;
    }

    public int getMes() {
        return mes;
    }

    public int getDia() {
        return dia;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public void setCantidadArticulo(Integer cantidadArticulo) {
        this.cantidadArticulo = cantidadArticulo;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
}
