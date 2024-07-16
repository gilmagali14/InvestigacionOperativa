package com.operativa.gestion.dto;

import java.time.LocalDateTime;

public class DemandaDto {

    String fechaDesde;
    String fechaHasta;
    Long idArticulo;

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Long getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Long idArticulo) {
        this.idArticulo = idArticulo;
    }

    public DemandaDto(String fechaDesde, String fechaHasta, Long idArticulo) {
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.idArticulo = idArticulo;
    }

    public DemandaDto() {

    }
}
