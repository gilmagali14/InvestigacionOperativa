package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ArticuloProveedor;
import com.operativa.gestion.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticuloProveedorRepository extends JpaRepository<ArticuloProveedor, Long> {

    Optional<ArticuloProveedor> findByArticuloAndProveedor(Articulo articulo, Proveedor proveedor);

    List<ArticuloProveedor> findAllByArticulo(Articulo articulo);

}
