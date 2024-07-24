package com.operativa.gestion.service.demanda;

import com.operativa.gestion.dto.PeriodoPrediccionDemandaDTO;
import com.operativa.gestion.dto.PrediccionDemandaModeloDTO;
import com.operativa.gestion.dto.ResultadosDemandaDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.TipoArticulo;

import java.util.List;

public interface PrediccionDemandaStrategy {

    public String getType();

    public Long create(PrediccionDemandaModeloDTO dto, TipoArticulo family, Articulo product) throws Exception;

    public void update(PrediccionDemandaModeloDTO dto) throws Exception;

    public List<PeriodoPrediccionDemandaDTO> predict(ResultadosDemandaDTO result, PrediccionDemandaModeloDTO model) throws Exception;
}
