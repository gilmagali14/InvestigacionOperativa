package com.operativa.gestion.model;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticulo;
    private String nombre;
    private String descripcion;
    private Double precio;
    private LocalDateTime fechaBaja;
    private Integer tasaRotacion;
    private int stock;

    @CreationTimestamp
    private LocalDateTime fechaAlta;

    @ManyToOne
    @JoinColumn(name = "codTipoArticulo", nullable = false)
    private TipoArticulo tipoArticulo;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<ArticuloVenta> articuloVentas;

    public Articulo(String nombre, String descripcion, Double precio, Integer tasaRotacion, TipoArticulo tipoArticulo,
                    int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tasaRotacion = tasaRotacion;
        this.tipoArticulo = tipoArticulo;
        this.stock = stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Articulo() {

    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Integer getTasaRotacion() {
        return tasaRotacion;
    }

    public void setTasaRotacion(Integer tasaRotacion) {
        this.tasaRotacion = tasaRotacion;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public void setOrdenesCompraDetalle(List<OrdenCompraDetalle> ordenesCompraDetalle) {
        this.ordenesCompraDetalle = ordenesCompraDetalle;
    }

    public void setArticuloVentas(List<ArticuloVenta> articuloVentas) {
        this.articuloVentas = articuloVentas;
    }
}
