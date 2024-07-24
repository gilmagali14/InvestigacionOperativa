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
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class IxDPS implements PrediccionDemandaStrategy {

    @Autowired
    private PrediccionDemandaRepository demandPredictionModelRepository;

    @Autowired
    private TipoModeloPrediccionDemandaRepository demandPredictionModelTypeRepository;

    @Autowired
    private ErrorCalculationSingleton errorCalculationSingleton;

    @Override
    public String getType() {
        return "Ix";
    }

    @Override
    public Long create(PrediccionDemandaModeloDTO dto, TipoArticulo family, Articulo product) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository.findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        IxModeloPrediccion model = new IxModeloPrediccion();
        model.setLength(dto.getLength());
        model.setExpectedDemand(dto.getExpectedDemand());
        model.setTipoModeloPrediccionDemanda(optDPMT.get());
        model.setPrediccionDemandaColor(dto.getColor());
        model.setDeleted(false);
        model.setArticulo(product);
        return demandPredictionModelRepository.save(model).getIdPrediccionDemanda();
    }

    @Override
    public void update(PrediccionDemandaModeloDTO dto) throws Exception {
        Optional<TipoModeloPrediccionDemanda> optDPMT = demandPredictionModelTypeRepository
                .findByTipoModeloPrediccionDemandaNombre(dto.getType());
        if(optDPMT.isEmpty()) {
            throw new Exception("No se encontró el tipo de modelo");
        }

        Optional<PrediccionDemanda> optDPM = demandPredictionModelRepository.findById(dto.getId());
        if(optDPM.isEmpty()) {
            throw new Exception("No se encontró el modelo");
        }
        IxModeloPrediccion ixDemandPredictionModel = (IxModeloPrediccion)optDPM.get();
        ixDemandPredictionModel.setLength(dto.getLength());
        ixDemandPredictionModel.setExpectedDemand(dto.getExpectedDemand());
        ixDemandPredictionModel.setTipoModeloPrediccionDemanda(optDPMT.get());
        ixDemandPredictionModel.setPrediccionDemandaColor(dto.getColor());
        demandPredictionModelRepository.save(ixDemandPredictionModel);
    }

    @Override
    public List<PeriodoPrediccionDemandaDTO> predict(ResultadosDemandaDTO result, PrediccionDemandaModeloDTO model) throws Exception {
        List<PeriodoPrediccionDemandaDTO> periods = new ArrayList<>();
        Integer length = model.getLength();
        Integer expectedDemand = model.getExpectedDemand();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);

        TreeMap<Integer, Double> ix = new TreeMap<>();

        Integer i = 0;
        AtomicInteger atomicCantPeriodos = new AtomicInteger(0);

        AtomicInteger atomicOffsetPredecirMesActual = new AtomicInteger(0);

        result.getPeriodos().forEach (p -> {
            if(p.getYear().equals(year) && p.getMonth().equals(month) && p.getValue() != null) atomicOffsetPredecirMesActual.set(1);
            if((p.getYear() < year || p.getYear().equals(year) && p.getMonth() < month)
                    || (p.getYear().equals(year) && p.getMonth().equals(month) && p.getValue() != null)) {
                atomicCantPeriodos.set(atomicCantPeriodos.get() + 1);
            }
        });

        Integer cantPeriodos = atomicCantPeriodos.get();
        Integer cantCiclos = cantPeriodos / length;
        Integer skip = cantPeriodos % length;
        Integer skipCopy = skip;

        if(cantCiclos == 0) throw new Exception("No hay suficientes datos para armar al menos un ciclo");

        Integer offsetPredecirMesActual = atomicOffsetPredecirMesActual.get();

        Integer sumaTotal = 0;

        for (PeriodoDemandaRealDTO period : result.getPeriodos()) {
            if (skipCopy > 0) {
                skipCopy--;
                continue;
            }
            Double value = 0.0;

            if (period.getValue() != null) {
                value = period.getValue().doubleValue();
            }
            Double prev = ix.getOrDefault(i, 0.0);

            ix.put(i, prev + value);
            sumaTotal += value.intValue();

            i = (i + 1) % length;
        }

        for (Map.Entry<Integer, Double> mes : ix.entrySet()) {
            Double prev = ix.get(mes.getKey());

            Double prom = prev / cantCiclos;
            ix.put(mes.getKey(), prom);
        }

        Double promedioTotal = sumaTotal.doubleValue() / cantCiclos.doubleValue();

        for (Map.Entry<Integer, Double> mes : ix.entrySet()) {
            Double prev = ix.get(mes.getKey());
            ix.put(mes.getKey(), prev/promedioTotal);
        }


        calendar.add(Calendar.MONTH, length + offsetPredecirMesActual);
        Date limite = calendar.getTime() ;
        calendar.add(Calendar.MONTH, -length);

        i = 0;

        Integer lastValue = 0;
        Integer lastPeriod = 0;
        for (PeriodoDemandaRealDTO period : result.getPeriodos()) {
            Integer aux = period.getMonth() - 1 + period.getYear() * 12;
            if (aux > lastPeriod && period.getValue() != null) {
                lastPeriod = aux;
                lastValue = period.getValue();
            }
        }

        PeriodoPrediccionDemandaDTO dto = new PeriodoPrediccionDemandaDTO();
        dto.setMonth(month - 1 + offsetPredecirMesActual);
        dto.setYear(year);
        dto.setPrediction(lastValue.doubleValue());
        dto.setError(null);
        periods.add(dto);

        while (calendar.getTime().before(limite)) {
            Double prediction = ix.get(i) * expectedDemand;
            Integer imonth = calendar.get(Calendar.MONTH) + 1;
            Integer iyear = calendar.get(Calendar.YEAR);

            PeriodoPrediccionDemandaDTO secondDto = new PeriodoPrediccionDemandaDTO();
            secondDto.setMonth(imonth);
            secondDto.setYear(iyear);
            secondDto.setPrediction(prediction);
            secondDto.setError(errorCalculationSingleton.getError(errorCalculationSingleton.getMetodoErrorGlobal(), null, prediction));
            periods.add(secondDto);

            calendar.add(Calendar.MONTH, 1);
            i = (i + 1) % length;
        }
        return periods;
    }
}
