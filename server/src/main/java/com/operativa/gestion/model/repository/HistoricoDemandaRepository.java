package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.HistoricoDemanda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoricoDemandaRepository extends JpaRepository<HistoricoDemanda, Long> {

    List<HistoricoDemanda> findHistoricoDemandaByArticuloAndAño(Articulo articulo, Integer año);

    Optional<HistoricoDemanda> findByArticuloAndAñoAndMes(Articulo articulo, Integer año, Integer mes);
}
