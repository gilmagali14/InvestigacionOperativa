package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ArticuloProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

}
