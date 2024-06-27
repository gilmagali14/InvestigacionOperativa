package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.EstadoOrdenDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoOrdenCompraRepository extends JpaRepository<EstadoOrdenDeCompra, Long> {

}