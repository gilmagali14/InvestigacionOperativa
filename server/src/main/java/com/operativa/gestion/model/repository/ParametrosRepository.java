package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Parametros;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParametrosRepository extends JpaRepository<Parametros, Long> {

    Parametros findByNombre(String nombre);
}
