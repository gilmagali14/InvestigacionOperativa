package com.operativa.gestion.service.demanda;


import com.operativa.gestion.dto.PeriodoDemandaRealDTO;
import com.operativa.gestion.dto.PeriodoPrediccionDemandaDTO;
import com.operativa.gestion.dto.PrediccionDemandaModeloDTO;
import com.operativa.gestion.dto.ResultadosDemandaDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.PrediccionDemandaRepository;
import com.operativa.gestion.model.repository.TipoModeloPrediccionDemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PMSEDPS implements PrediccionDemandaStrategy {

    @Autowired
    private PrediccionDemandaRepository demandPredictionModelRepository;

    @Autowired
    private TipoModeloPrediccionDemandaRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "PMSE";
    }
    @Override
    public Long create(PrediccionDemandaModeloDTO dto, TipoArticulo family, Articulo product) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository.findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        PMSEModeloPrediccion model = new PMSEModeloPrediccion();
        model.setAlpha(dto.getAlpha());
        model.setRoot(dto.getRoot());
        model.setTipoModeloPrediccionDemanda(optDPMT.get());
        model.setPrediccionDemandaColor(dto.getColor());
        model.setDeleted(false);
        model.setArticulo(product);
        return demandPredictionModelRepository.save(model).getIdPrediccionDemanda();
    }

    @Override
    public void update(PrediccionDemandaModeloDTO dto) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository.findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        Optional<PrediccionDemanda> optDPM = demandPredictionModelRepository.findById(dto.getId());
        if(optDPM.isEmpty()) {
            throw new Exception("No se encontró el modelo");
        }

        PMSEModeloPrediccion model = (PMSEModeloPrediccion)optDPM.get();
        model.setAlpha(dto.getAlpha());
        model.setRoot(dto.getRoot());
        model.setTipoModeloPrediccionDemanda(optDPMT.get());
        model.setPrediccionDemandaColor(dto.getColor());
        demandPredictionModelRepository.save(model);
    }

    @Override
    public List<PeriodoPrediccionDemandaDTO> predict(ResultadosDemandaDTO result, PrediccionDemandaModeloDTO model) throws Exception {
        List<PeriodoPrediccionDemandaDTO> periods = new ArrayList<>();
        Double a = model.getAlpha();
        Double prev = null;
        Integer trdPrev = null;

        Integer maxMonth = 0;
        Integer maxYear = 0;
        for (PeriodoDemandaRealDTO period : result.getPeriodos()) {
            if (prev == null) {
                prev = model.getRoot();
            } else {
                prev = prev + a * (trdPrev - prev);
            }

            PeriodoPrediccionDemandaDTO dto = new PeriodoPrediccionDemandaDTO();
            dto.setMonth(period.getMonth());
            dto.setYear(period.getYear());
            dto.setPrediction(prev);
            dto.setError(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), period.getValue(), prev));
            periods.add(dto);

            if (period.getValue() == null) {
                trdPrev = prev.intValue();
            } else {
                trdPrev = period.getValue();
            }

            if(period.getYear() > maxYear || period.getYear().equals(maxYear) && period.getMonth() - 1  > maxMonth) {
                maxYear = period.getYear();
                maxMonth = period.getMonth() - 1;
            }
        }
        return periods;
    }
}
