package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ProximoPeriodoPrediccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProximoPeriodoPrediccionRepository extends JpaRepository<ProximoPeriodoPrediccion, Long> {

    Optional<ProximoPeriodoPrediccion> findByArticuloAndAñoAndMonth(Articulo articulo, Integer year, Integer month);

}
