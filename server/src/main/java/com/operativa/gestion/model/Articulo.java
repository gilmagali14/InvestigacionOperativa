package com.operativa.gestion.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codArticulo;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Long numeroLote;

    private LocalDateTime fechaBaja;

    private BigDecimal costoAlmacenamiento;

    public BigDecimal getCostoAlmacenamiento() {
        return costoAlmacenamiento;
    }

    public void setCostoAlmacenamiento(BigDecimal costoAlmacenamiento) {
        this.costoAlmacenamiento = costoAlmacenamiento;
    }

    @CreationTimestamp
    private LocalDateTime fechaAlta;

    @ManyToOne
    @JoinColumn(name = "codTipoArticulo", nullable = false)
    private TipoArticulo tipoArticulo;

    @ManyToOne
    @JoinColumn(name = "codProveedor", nullable = false)
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "codInventario")
    private Inventario inventario;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<OrdenCompraDetalle> ordenesCompraDetalle;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL)
    private List<ArticuloVenta> articuloVentas;

    public Articulo(String nombre, String descripcion, BigDecimal precio,
                    TipoArticulo tipoArticulo, Proveedor proveedor, BigDecimal costoAlmacenamiento) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoArticulo = tipoArticulo;
        this.proveedor = proveedor;
        this.costoAlmacenamiento = costoAlmacenamiento;
    }

    public Articulo() {
    }

    public Long getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(Long codArticulo) {
        this.codArticulo = codArticulo;
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

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getNumeroLote() {
        return numeroLote;
    }

    public void setNumeroLote(Long numeroLote) {
        this.numeroLote = numeroLote;
    }

    public LocalDateTime getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDateTime fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setTipoArticulo(TipoArticulo tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public void setOrdenesCompraDetalle(List<OrdenCompraDetalle> ordenesCompraDetalle) {
        this.ordenesCompraDetalle = ordenesCompraDetalle;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    @Nullable
    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(@Nullable LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}
