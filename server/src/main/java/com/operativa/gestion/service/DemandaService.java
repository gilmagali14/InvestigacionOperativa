package com.operativa.gestion.service;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DemandaService {

    private final ArticuloRepository articuloRepository;
    private final OrdenDeCompraService ordenDeCompraService;
    private final DemandaRepository demandaRepository;
    private final HistoricoDemandaRepository historicoDemandaRepository;
    private final ParametrosRepository parametrosRepository;
    private final PrediccionDemandaRepository prediccionDemandaRepository;
    private final ProximoPeriodoPrediccionRepository proximoPeriodoPrediccionRepository;

    public DemandaService(ArticuloRepository articuloRepository, OrdenDeCompraService ordenDeCompraService,
                          DemandaRepository demandaRepository, HistoricoDemandaRepository historicoDemandaRepository, ParametrosRepository parametrosRepository, PrediccionDemandaRepository prediccionDemandaRepository, ProximoPeriodoPrediccionRepository proximoPeriodoPrediccionRepository) {
        this.articuloRepository = articuloRepository;
        this.ordenDeCompraService = ordenDeCompraService;
        this.demandaRepository = demandaRepository;
        this.historicoDemandaRepository = historicoDemandaRepository;
        this.parametrosRepository = parametrosRepository;
        this.prediccionDemandaRepository = prediccionDemandaRepository;
        this.proximoPeriodoPrediccionRepository = proximoPeriodoPrediccionRepository;
    }

    public BigDecimal calcularDemandaAnual(DemandaDto demandaDto) {
        List<Demanda> demandas = demandaRepository.findDemandaByPeriodos(demandaDto.getIdArticulo(), demandaDto.getFechaDesde(),
                demandaDto.getFechaHasta());

        BigDecimal demanda = BigDecimal.ZERO;
        int sumaPeso = 0;
        int pesoInicial = demandas.size();

        for (int i = 0; i < demandas.size(); i++) {
            Demanda d = demandas.get(i);
            int pesoValor = pesoInicial - i;
            sumaPeso += pesoValor;
            BigDecimal cantidadArticulos = BigDecimal.valueOf(d.getCantidad());
            BigDecimal pesoBigDecimal = BigDecimal.valueOf(pesoValor);
            demanda = demanda.add(cantidadArticulos.multiply(pesoBigDecimal));
        }

        if (sumaPeso == 0) {
            return BigDecimal.ZERO;
        }

        return demanda.divide(BigDecimal.valueOf(sumaPeso), 2, BigDecimal.ROUND_HALF_UP);
    }


    public List<Demanda> obtenerDemandas() {
        return demandaRepository.findAll();
    }

    public List<Map<String, Double>> calcularError() {
        List<Demanda> demandas = demandaRepository.findAll();
        double suma1 = 0;
        double suma2 = 0;
        double suma3 = 0;

        double errorMedioPorcentualAbs;
        List<Map<String, Double>> lista = new ArrayList<>();
        for (Demanda demanda : demandas) {
            Map<String, Double> errors = new HashMap<>();
            double errorMedioAbs = Math.abs(demanda.getCantidad() - demanda.getPronostico());
            double errorMedioAbsCuadrado = Math.pow(errorMedioAbs, 2);
            errorMedioPorcentualAbs = errorMedioAbs / demanda.getCantidad();
            suma1 = suma1 + errorMedioAbs;
            suma2 = suma2 + errorMedioAbsCuadrado;
            suma3 = suma3 + errorMedioPorcentualAbs;
            errors.put("MAE", errorMedioAbs);
            errors.put("RMSQ", errorMedioAbsCuadrado);
            errors.put("MAPE", errorMedioPorcentualAbs);
            lista.add(errors);
        }
        Map<String, Double> total = new HashMap<>();
        total.put("MAE", suma1);
        total.put("RMSQ", suma2);
        total.put("MAPE", suma3);
        lista.add(total);
        return lista;
    }

    public Collection<PrediccionDemandaModeloDTO> obtenerModelos(Long id, Boolean family) throws Exception {
        List<PrediccionDemandaModeloDTO> ret = new ArrayList<>();
        HashMap<String, Integer> nums = new HashMap<>();
        Articulo articulo = articuloRepository.findById(id).get();

        for (PrediccionDemanda modelo : prediccionDemandaRepository.findByArticulo(articulo)) {
            String type = modelo.getTipoModeloPrediccionDemanda().getTipoModeloPrediccionDemandaNombre();

            if(!nums.containsKey(type)) {
                nums.put(type, 1);
            }
            Integer currentNum = nums.get(type);
            PrediccionDemandaModeloDTO dto = new PrediccionDemandaModeloDTO();
            dto.setId(modelo.getIdPrediccionDemanda());
            dto.setType(modelo.getTipoModeloPrediccionDemanda().getTipoModeloPrediccionDemandaNombre());
            dto.setColor(modelo.getPrediccionDemandaColor());
            dto.setNum(currentNum);

            nums.replace(type, currentNum + 1);

            switch (type) {
                case "PMP":
                    dto.setPonderations(((PMPModeloPrediccion)modelo).getPonderations());
                    break;
                case "PMSE":
                    dto.setAlpha(((PMSEModeloPrediccion)modelo).getAlpha());
                    dto.setRoot(((PMSEModeloPrediccion)modelo).getRoot());
                    break;
                case "RL":
                    dto.setIgnorePeriods(((RLModeloPrediccion)modelo).getIgnorePeriods());
                    break;
                case "Ix":
                    dto.setLength(((IxModeloPrediccion)modelo).getLength());
                    dto.setExpectedDemand(((IxModeloPrediccion)modelo).getExpectedDemand());
                    break;
                default:
                    throw new Exception("Tipo de modelo no soportado");
            }
            ret.add(dto);
        }
        return ret;
    }


}

