package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.TipoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TipoArticuloRespository extends JpaRepository<TipoArticulo, Long> {
    @Query("SELECT ta FROM TipoArticulo ta WHERE ta.nombre = :nombreTipoArticulo")
    Optional<TipoArticulo> findByNombre(@Param("nombreTipoArticulo") String nombreTipoArticulo);
}
