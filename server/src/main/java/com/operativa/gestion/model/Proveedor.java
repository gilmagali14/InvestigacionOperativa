package com.operativa.gestion.model;

import jakarta.persistence.*;

@Entity
public class Proveedor {

    @Id
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Proveedor (String nombre) {
        this.nombre = nombre;

    }

    public Proveedor() {

    }
}
