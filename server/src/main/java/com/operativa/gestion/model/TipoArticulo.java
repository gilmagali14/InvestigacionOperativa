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

    private String nombreTipoArticulo;

    private LocalDateTime fechaBajaTipoArticulo;

    @OneToMany(mappedBy = "tipoArticulo")
    private List<Articulo> articulos;

    public Long getCodTipoArticulo() {
        return codTipoArticulo;
    }

    public void setCodTipoArticulo(Long codTipoArticulo) {
        this.codTipoArticulo = codTipoArticulo;
    }

    public String getNombreTipoArticulo() {
        return nombreTipoArticulo;
    }

    public void setNombreTipoArticulo(String nombreTipoArticulo) {
        this.nombreTipoArticulo = nombreTipoArticulo;
    }

    public LocalDateTime getFechaBajaTipoArticulo() {
        return fechaBajaTipoArticulo;
    }

    public void setFechaBajaTipoArticulo(LocalDateTime fechaBajaTipoArticulo) {
        this.fechaBajaTipoArticulo = fechaBajaTipoArticulo;
    }
}
