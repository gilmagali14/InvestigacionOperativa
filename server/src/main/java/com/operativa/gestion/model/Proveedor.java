package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    private String nombre;

    @OneToMany(mappedBy = "proveedor")
    private List<Articulo> articulos;

    private BigDecimal costoPedido;

    private Long tiempoDemora;

    public BigDecimal getCostoPedido() {
        return costoPedido;
    }

    public void setCostoPedido(BigDecimal costoPedido) {
        this.costoPedido = costoPedido;
    }

    public Long getTiempoDemora() {
        return tiempoDemora;
    }

    public void setTiempoDemora(Long tiempoDemora) {
        this.tiempoDemora = tiempoDemora;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Proveedor (String nombre) {
        this.nombre = nombre;

    }

    public Proveedor() {

    }
}
