package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

}
