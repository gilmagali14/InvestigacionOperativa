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

import java.util.*;

@Component
public class PMPDPS implements PrediccionDemandaStrategy {

    @Autowired
    private PrediccionDemandaRepository demandPredictionModelRepository;
    @Autowired
    private TipoModeloPrediccionDemandaRepository demandPredictionModelTypeRepository;
    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;
    @Autowired
    private PrediccionDemandaRepository prediccionDemandaRepository;

    @Override
    public String getType() {
        return "PMP";
    }

    @Override
    public Long create(PrediccionDemandaModeloDTO dto, TipoArticulo family, Articulo product) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository.findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        PMPModeloPrediccion model = new PMPModeloPrediccion();
        model.setPonderations(dto.getPonderations());
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

        PMPModeloPrediccion model = (PMPModeloPrediccion) optDPM.get();
        model.setPonderations(dto.getPonderations());
        model.setTipoModeloPrediccionDemanda(optDPMT.get());
        model.setPrediccionDemandaColor(dto.getColor());
        demandPredictionModelRepository.save(model);
    }

    public Collection<Double> getRowPonderations(PMPModeloPrediccion pmpModeloPrediccion) {
        Collection<String> strs =  Arrays.asList(pmpModeloPrediccion.getPonderations().split(";"));
        Collection<Double> ret = new ArrayList<>();
        for (String str : strs) {
            ret.add(Double.parseDouble(str));
        }
        return ret;
    }
    @Override
    public List<PeriodoPrediccionDemandaDTO> predict(ResultadosDemandaDTO result, PrediccionDemandaModeloDTO model) throws Exception {
        List<PeriodoPrediccionDemandaDTO> periods = new ArrayList<>();
        ArrayList<Double> ponderaciones = new ArrayList<>(getRowPonderations(((PMPModeloPrediccion)
                prediccionDemandaRepository.findById(model.getId()).get())));
        Double sumaPonderaciones = 0.0;
        for (Double ponderacion : ponderaciones) {
            sumaPonderaciones += ponderacion;
        }
        LinkedList<Integer> prev = new LinkedList<>();
        Integer maxMonth = 0;
        Integer maxYear = 0;
        for (PeriodoDemandaRealDTO period : result.getPeriodos()) {
            if (prev.size() < ponderaciones.size()) {
                if (period.getValue() == null) {
                    prev.addLast(0);
                } else {
                    prev.addLast(period.getValue());
                }
            } else {
                Double value = 0.0;
                for(int i = 0; i < ponderaciones.size(); i++) {
                    value += ponderaciones.get(i) * prev.get(i) / sumaPonderaciones;
                }
                PeriodoPrediccionDemandaDTO periodoPrediccionDemandaDTO = new PeriodoPrediccionDemandaDTO();
                periodoPrediccionDemandaDTO.setMonth(period.getMonth());
                periodoPrediccionDemandaDTO.setYear(period.getYear());
                periodoPrediccionDemandaDTO.setPrediction(value);
                periodoPrediccionDemandaDTO.setError(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), period.getValue(), value));
                periods.add(periodoPrediccionDemandaDTO);

                prev.removeFirst();

                if (period.getValue() == null) {
                    prev.addLast(value.intValue());
                } else {
                    prev.addLast(period.getValue());
                }

                if(period.getYear() > maxYear || period.getYear().equals(maxYear) && period.getMonth() - 1  > maxMonth) {
                    maxYear = period.getYear();
                    maxMonth = period.getMonth() - 1;
                }
            }
        }
        return periods;
    }
}
