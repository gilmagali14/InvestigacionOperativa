package com.operativa.gestion.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Parametros {

    @Id
    @JsonProperty(value = "parameterId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParametro;
    private String nombre;
    private String valor;
    private Boolean isDeleted;

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(Long idParametro) {
        this.idParametro = idParametro;
    }
}

