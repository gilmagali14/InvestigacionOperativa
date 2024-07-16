package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdenCompraDetalleRepository extends JpaRepository<OrdenCompraDetalle, Long> {

    @Query("SELECT ocd FROM OrdenCompraDetalle ocd WHERE ocd.articulo.idArticulo = :articuloId")
    List<OrdenCompraDetalle> findByArticuloId(@Param("articuloId") Long articuloId);

    @Query("SELECT ocd FROM OrdenCompraDetalle ocd WHERE ocd.articulo.idArticulo = :articuloId and " +
            "ocd.ordenDeCompra.estadoOrdenDeCompra.nombreEstadoOrdenDeCompra = 'PENDIENTE'")
    List<OrdenCompraDetalle> findByArticuloIdAndStatus(@Param("articuloId") Long articuloId);

    List<OrdenCompraDetalle> findByArticulo(Articulo articulo);

}
