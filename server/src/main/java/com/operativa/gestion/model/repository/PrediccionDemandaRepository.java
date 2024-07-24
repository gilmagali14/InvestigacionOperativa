package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.PrediccionDemanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrediccionDemandaRepository extends JpaRepository<PrediccionDemanda, Long> {
    List<PrediccionDemanda> findByArticulo(Articulo articulo);
}
