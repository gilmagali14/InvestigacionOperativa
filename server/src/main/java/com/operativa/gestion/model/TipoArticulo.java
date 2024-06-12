package com.operativa.gestion.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class TipoArticulo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long codTipoArticulo;

    private String nombre;

    private LocalDateTime fechaBaja;

    @OneToMany(mappedBy = "tipoArticulo")
    private List<Articulo> articulos;

    public Long getCodTipoArticulo() {
        return codTipoArticulo;
    }

    public void setCodTipoArticulo(Long codTipoArticulo) {
        this.codTipoArticulo = codTipoArticulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
}
