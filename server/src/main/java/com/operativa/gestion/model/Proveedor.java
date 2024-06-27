package com.operativa.gestion.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long codProveedor;

    private String nombre;

    @OneToMany(mappedBy = "proveedor")
    private List<Articulo> articulos;

    public Long getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(Long codProveedor) {
        this.codProveedor = codProveedor;
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

    public Proveedor (Long codProveedor, String nombre) {
        this.codProveedor = codProveedor;
        this.nombre = nombre;

    }

    public Proveedor() {};
}
