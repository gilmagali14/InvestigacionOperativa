package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Demanda;
import com.operativa.gestion.model.OrdenDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
}
