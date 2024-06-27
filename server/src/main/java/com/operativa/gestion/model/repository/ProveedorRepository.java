package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.TipoArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT ta FROM Proveedor ta WHERE ta.nombre = :nombre")
    Optional<Proveedor> findByNombre(@Param("nombre") String nombre);
}
