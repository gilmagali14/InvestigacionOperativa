package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.TipoModeloPrediccionDemanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoModeloPrediccionDemandaRepository extends JpaRepository<TipoModeloPrediccionDemanda, Long> {
    Optional<TipoModeloPrediccionDemanda> findByTipoModeloPrediccionDemandaNombre(String tipo);
}