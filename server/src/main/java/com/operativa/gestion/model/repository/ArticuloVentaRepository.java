package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.ArticuloVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticuloVentaRepository extends JpaRepository<ArticuloVenta, Long> {
    @Query("SELECT av FROM ArticuloVenta av " +
            "WHERE av.articulo.idArticulo = :articuloId " +
            "AND av.fechaVenta BETWEEN :fechaDesde AND :fechaHasta " +
            "ORDER BY av.fechaVenta DESC")
    List<ArticuloVenta> findArticuloVentaByArticuloIdAndFechaVentaBetween(@Param("articuloId") Long articuloId,
                                                                          @Param("fechaDesde") String fechaDesde,
                                                                          @Param("fechaHasta") String fechaHasta);

    @Query("SELECT av FROM ArticuloVenta av WHERE av.articulo.idArticulo = :articuloId " +
            "AND av.fechaVenta >= :fechaInicio AND av.fechaVenta < :fechaFin")
    List<ArticuloVenta> findByArticuloIdAndFechaVentaBetween(@Param("articuloId") Long articuloId,
                                                             @Param("fechaInicio") String fechaInicio,
                                                             @Param("fechaFin") String fechaFin
    );

    @Query("SELECT av FROM ArticuloVenta av WHERE av.articulo.idArticulo = :articuloId ")
    List<ArticuloVenta> findArticuloVentaByArticuloId(@Param("articuloId") Long articuloId);

}
