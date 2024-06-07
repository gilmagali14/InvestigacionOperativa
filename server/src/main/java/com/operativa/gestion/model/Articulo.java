package com.operativa.gestion.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long codArticulo;

    private String nombre;

    private String descripcion;

    @Timestamp
    @Nullable
    private LocalDateTime bajaArticulo;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "codTipoArticulo")
    private TipoArticulo tipoArticulo;

  /*  @ManyToOne
    @JoinColumn(name = "codInventario")
    private Inventario inventario;

    @ManyToMany(mappedBy = "articulos")
    private List<OrdenDeCompra> ordenesDeCompra;
    */

    public Articulo(String nombre, String descripcion, TipoArticulo tipoArticulo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipoArticulo = tipoArticulo;
    }

    public Articulo() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setId(Long id) {
        this.codArticulo = id;
    }

    public Long getId() {
        return codArticulo;
    }
}
