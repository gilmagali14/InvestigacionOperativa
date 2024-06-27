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

    @CreationTimestamp
    private LocalDateTime fechaActualizacion;

    @OneToMany(mappedBy = "ordenDeCompra", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;

    @ManyToOne
    @JoinColumn(name = "codEstadoOrdenDeCompra", nullable = false)
    private EstadoOrdenDeCompra estadoOrdenDeCompra;

    public List<OrdenCompraDetalle> getOrdenesCompraDetalle() {
        return ordenesCompraDetalle;
    }

    public void setOrdenesCompraDetalle(List<OrdenCompraDetalle> ordenesCompraDetalle) {
        this.ordenesCompraDetalle = ordenesCompraDetalle;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdOrdenDeCompra() {
        return idOrdenDeCompra;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public EstadoOrdenDeCompra getEstadoOrdenDeCompra() {return estadoOrdenDeCompra;}

    public void setEstadoOrdenDeCompra(EstadoOrdenDeCompra estadoOrdenDeCompra) {this.estadoOrdenDeCompra = estadoOrdenDeCompra;}

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}