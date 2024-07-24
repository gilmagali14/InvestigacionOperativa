package com.operativa.gestion.service.demanda;

import com.operativa.gestion.dto.PeriodoDemandaRealDTO;
import com.operativa.gestion.dto.PeriodoPrediccionDemandaDTO;
import com.operativa.gestion.dto.PrediccionDemandaModeloDTO;
import com.operativa.gestion.dto.ResultadosDemandaDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.ParametrosRepository;
import com.operativa.gestion.model.repository.PrediccionDemandaRepository;
import com.operativa.gestion.model.repository.TipoModeloPrediccionDemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Component
public class RLDPS implements PrediccionDemandaStrategy {

    @Autowired
    private PrediccionDemandaRepository demandPredictionModelRepository;

    @Autowired
    private TipoModeloPrediccionDemandaRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Autowired
    ParametrosRepository parameterRepository;

    @Override
    public String getType() {
        return "RL";
    }
    @Override
    public Long create(PrediccionDemandaModeloDTO dto, TipoArticulo family, Articulo product) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository.findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        RLModeloPrediccion model = new RLModeloPrediccion();
        model.setIgnorePeriods(dto.getIgnorePeriods());
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

        RLModeloPrediccion model = (RLModeloPrediccion)optDPM.get();
        model.setIgnorePeriods(dto.getIgnorePeriods());
        model.setTipoModeloPrediccionDemanda(optDPMT.get());
        model.setPrediccionDemandaColor(dto.getColor());
        demandPredictionModelRepository.save(model);
    }

    @Override
    public List<PeriodoPrediccionDemandaDTO> predict(ResultadosDemandaDTO result, PrediccionDemandaModeloDTO model) throws Exception {
        List<PeriodoPrediccionDemandaDTO> periods = new ArrayList<>();
        Integer ignore = model.getIgnorePeriods();
        Integer predict;
        try {
            predict = Integer.valueOf(parameterRepository.findByNombre("PERIODOS_A_PREDECIR").getValor());
        } catch (NumberFormatException nfe) {
            throw new Exception("Número de periodos a predecir no válido");
        }

        Integer sumX = 0;
        Integer sumY = 0;
        BigDecimal sumX2 = BigDecimal.valueOf(0);
        BigDecimal sumXY = BigDecimal.valueOf(0);

        Integer n = 0;

        TreeMap<Integer, Integer> l = new TreeMap<>();

        Integer minX = 120000;
        Integer maxX = 0;

        for (PeriodoDemandaRealDTO period : result.getPeriodos()) {
            if (period.getValue() != null) {
                Integer x = period.getMonth() - 1 + period.getYear() * 12;
                l.put(x, period.getValue());
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
            }
        }

        SortedMap<Integer, Integer> sl = l.tailMap(minX + ignore);

        for (Map.Entry<Integer, Integer> e : sl.entrySet()) {
            Integer x = e.getKey();
            Integer y = e.getValue();
            n += 1;
            sumX += x;
            sumY += y;
            sumX2 = sumX2.add(BigDecimal.valueOf(Math.pow((double) x, 2.0)));
            sumXY = sumXY.add(BigDecimal.valueOf((long) x * y));
        }

        Double promX = (double) sumX / n;
        Double promY = (double) sumY / n;

        Double b = (sumXY.subtract(BigDecimal.valueOf(n * promX * promY))).divide(sumX2.subtract(BigDecimal.valueOf(n * Math.pow(promX, 2))), 12, RoundingMode.HALF_UP).doubleValue();
        Double a = promY - b * promX;

        for (Integer i = 1; i <= predict; i++) {
            sl.put(maxX + i, null);
        }

        for (Map.Entry<Integer, Integer> e : sl.entrySet()) {
            Integer month = (e.getKey() % 12 + 1);
            Integer year = (e.getKey() / 12);
            Integer x = e.getKey();
            Integer y = e.getValue();
            Double value = a + x * b;

            PeriodoPrediccionDemandaDTO periodoPrediccionDemandaDTO = new PeriodoPrediccionDemandaDTO();
            periodoPrediccionDemandaDTO.setMonth(month);
            periodoPrediccionDemandaDTO.setYear(year);
            periodoPrediccionDemandaDTO.setPrediction(value);
            periodoPrediccionDemandaDTO.setError(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), y, value));
            periods.add(periodoPrediccionDemandaDTO);
        }
        return periods;
    }
}
