package com.operativa.gestion.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrdenDeCompra {

    @Id
    @GeneratedValue
    private Long idOrdenDeCompra;

    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    private String estado;

    @CreationTimestamp
    private LocalDateTime fechaActualizacion;
    @ManyToMany
    @JoinTable(
            name = "orden_articulo",
            joinColumns = @JoinColumn(name = "idOrdenDeCompra"),
            inverseJoinColumns = @JoinColumn(name = "articulo_id")
    )
    private List<Articulo> articulos;

    public Long getIdOrdenDeCompra() {
        return idOrdenDeCompra;
    }

    public void setIdOrdenDeCompra(Long idOrdenDeCompra) {
        this.idOrdenDeCompra = idOrdenDeCompra;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }
}