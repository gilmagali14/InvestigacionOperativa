package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Inventario findByIdArticulo(Long idArticulo);

    List<Inventario> findAllByModelo(@Param("modelo") String modelo);

}

