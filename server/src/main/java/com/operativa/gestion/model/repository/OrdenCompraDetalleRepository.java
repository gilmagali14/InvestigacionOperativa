package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.OrdenCompraDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdenCompraDetalleRepository  extends JpaRepository<OrdenCompraDetalle, Long> {
   // @Query("SELECT ocd.estado FROM OrdenCompraDetalle ocd where ocd.articulo.codArticulo = ?1")
    //List<String> findArticuloIds(Long codArticulo);
}
