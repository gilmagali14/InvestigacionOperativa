package com.operativa.gestion.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EstadoOrdenDeCompra {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long codEstadoOrdenDeCompra;

    private String nombreEstadoOrdenDeCompra;

    @CreationTimestamp
    private LocalDateTime fechaBajaEstadoOrdenDeCompra;

    @OneToMany(mappedBy = "estadoOrdenDeCompra")
    private List<OrdenDeCompra> ordenDeCompras;

    public Long getCodEstadoOrdenDeCompra() {return codEstadoOrdenDeCompra;}

    public void setCodEstadoOrdenDeCompra(Long codEstadoOrdenDeCompra) {this.codEstadoOrdenDeCompra = codEstadoOrdenDeCompra;}

    public String getNombreEstadoOrdenDeCompra() {return nombreEstadoOrdenDeCompra;}

    public void setNombreEstadoOrdenDeCompra(String nombreEstadoOrdenDeCompra) {this.nombreEstadoOrdenDeCompra = nombreEstadoOrdenDeCompra;}

    public LocalDateTime getFechaBajaEstadoOrdenDeCompra() {return fechaBajaEstadoOrdenDeCompra;}

    public void setFechaBajaEstadoOrdenDeCompra(LocalDateTime fechaBajaEstadoOrdenDeCompra) {this.fechaBajaEstadoOrdenDeCompra = fechaBajaEstadoOrdenDeCompra;}

    public void setOrdenDeCompras(List<OrdenDeCompra> ordenDeCompras) {this.ordenDeCompras = ordenDeCompras;}
}
