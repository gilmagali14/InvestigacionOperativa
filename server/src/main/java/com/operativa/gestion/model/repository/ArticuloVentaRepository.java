package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.ArticuloVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticuloVentaRepository extends JpaRepository<ArticuloVenta, Long> {
    @Query("SELECT av FROM ArticuloVenta av " +
            "WHERE av.articulo.codArticulo = :articuloId " +
            "AND av.fechaVenta BETWEEN :fechaDesde AND :fechaHasta " +
            "ORDER BY av.fechaVenta DESC")
    List<ArticuloVenta> findArticuloVentaByArticuloIdAndFechaVentaBetween(@Param("articuloId") Long articuloId,
                                                                          @Param("fechaDesde") LocalDateTime fechaDesde,
                                                                          @Param("fechaHasta") LocalDateTime fechaHasta);

    @Query("SELECT av FROM ArticuloVenta av " +
            "WHERE av.articulo.codArticulo = :articuloId " +
            "AND av.fechaVenta >= :inicioMesActual " +
            "AND av.fechaVenta < :inicioMesSiguiente")
    List<ArticuloVenta> findByArticuloIdAndFechaVentaBetween(
            @Param("articuloId") Long articuloId,
            @Param("inicioMesActual") LocalDateTime inicioMesActual,
            @Param("inicioMesSiguiente") LocalDateTime inicioMesSiguiente
    );

    List<ArticuloVenta> findByArticuloId(Long articuloId);

}
