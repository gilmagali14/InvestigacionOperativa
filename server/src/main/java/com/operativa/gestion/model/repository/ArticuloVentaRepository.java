package com.operativa.gestion.model.repository;

import com.operativa.gestion.dto.VentaPeriodoDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ArticuloVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticuloVentaRepository extends JpaRepository<ArticuloVenta, Long> {
    @Query("SELECT av FROM ArticuloVenta av WHERE av.articulo.idArticulo = :articuloId ")
    List<ArticuloVenta> findArticuloVentaByArticuloId(@Param("articuloId") Long articuloId);

    List<ArticuloVenta> findArticuloVentaByArticuloAndAno(Articulo articulo, int mes);

    List<ArticuloVenta> findArticuloVentaByAnoAndMes(int ano, int mes);

    List<ArticuloVenta> findArticuloVentaByAno(int ano);


}
