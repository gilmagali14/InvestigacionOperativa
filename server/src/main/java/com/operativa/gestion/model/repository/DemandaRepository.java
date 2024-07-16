package com.operativa.gestion.model.repository;

import com.operativa.gestion.model.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    @Query("SELECT d FROM Demanda d WHERE d.idArticulo = :idArticulo " +
            "AND d.mesPeriodoInicio = :fechaInicio")
        List<Demanda> findDemandasByFechas(@Param("idArticulo") Long idArticulo,
                                           @Param("fechaInicio") String fechaInicio);

    @Query("SELECT d FROM Demanda d WHERE d.idArticulo = :articuloId " +
            "AND (d.mesPeriodoInicio BETWEEN :fechaDesde AND :fechaHasta " +
            "OR d.mesPeriodoFin BETWEEN :fechaDesde AND :fechaHasta) " +
            "ORDER BY d.mesPeriodoInicio DESC")
    List<Demanda> findDemandaByPeriodos(@Param("articuloId") Long articuloId,
                                        @Param("fechaDesde") String fechaDesde,
                                        @Param("fechaHasta") String fechaHasta);
}



