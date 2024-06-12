package com.operativa.gestion.model;

import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "ordenDeCompra", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;

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
}