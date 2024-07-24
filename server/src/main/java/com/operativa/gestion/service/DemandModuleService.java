package com.operativa.gestion.service;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import com.operativa.gestion.service.demanda.PrediccionDemandaFactory;
import com.operativa.gestion.service.demanda.PrediccionDemandaStrategy;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DemandModuleService {

    private final TipoModeloPrediccionDemandaRepository tipoModeloPrediccionDemandaRepository;
    private final PrediccionDemandaRepository prediccionDemandaRepository;
    private final ProximoPeriodoPrediccionRepository proximoPeriodoPrediccionRepository;
    private final ArticuloRepository articuloRepository;
    private final ParametrosRepository parametrosRepository;
    private final HistoricoDemandaRepository historicoDemandaRepository;
    private final PrediccionDemandaFactory prediccionDemandaFactory;

    public DemandModuleService(TipoModeloPrediccionDemandaRepository tipoModeloPrediccionDemandaRepository, PrediccionDemandaRepository prediccionDemandaRepository, ProximoPeriodoPrediccionRepository proximoPeriodoPrediccionRepository, ArticuloRepository articuloRepository, ParametrosRepository parametrosRepository, HistoricoDemandaRepository historicoDemandaRepository, PrediccionDemandaFactory prediccionDemandaFactory) {
        this.tipoModeloPrediccionDemandaRepository = tipoModeloPrediccionDemandaRepository;
        this.prediccionDemandaRepository = prediccionDemandaRepository;
        this.proximoPeriodoPrediccionRepository = proximoPeriodoPrediccionRepository;
        this.articuloRepository = articuloRepository;
        this.parametrosRepository = parametrosRepository;
        this.historicoDemandaRepository = historicoDemandaRepository;
        this.prediccionDemandaFactory = prediccionDemandaFactory;
    }

    public Collection<PrediccionDemandaModeloDTO> obtenerModelos(Long id) throws Exception {
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

    public ResultadosDemandaDTO predecirDemanda(Long id, Boolean family, Date desde, Boolean predecirMesActual) throws Exception {
        Integer cantPeriodosAPredecir;
        try {
            cantPeriodosAPredecir = Integer.valueOf(parametrosRepository.findByNombre("PERIODOS_A_PREDECIR").getValor());
        } catch (NumberFormatException nfe) {
            throw new Exception("Número de periodos a predecir no válido");
        }
        ResultadosDemandaDTO ret = new ResultadosDemandaDTO();
        ret.setErrorAceptable(Double.valueOf(parametrosRepository.findByNombre("ERROR_ACEPTABLE").getValor()));

        Collection<PrediccionDemandaModeloDTO> models = obtenerModelos(id, family);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();

        Integer currMonth = calendar.get(Calendar.MONTH);
        Integer currYear = calendar.get(Calendar.YEAR);

        ProximoPeriodoDemandaDTO npd = new ProximoPeriodoDemandaDTO();

        if (predecirMesActual) {
            npd.setMonth(currMonth + 1);
            npd.setYear(currYear);
        } else {
            npd.setMonth((currMonth + 1) % 12 + 1);
            npd.setYear(currMonth == 11 ? currYear + 1 : currYear);
        }

        if (!family) {
            Optional<Articulo> optionalProduct = articuloRepository.findById(id);
            if(optionalProduct.isEmpty()) throw new Exception("No se encontró el producto");
            Articulo product = optionalProduct.get();
            Optional<ProximoPeriodoPrediccion> opt = proximoPeriodoPrediccionRepository.findByArticuloAndAñoAndMonth(product,
                    npd.getMonth(), npd.getYear());
            if(opt.isPresent()) {
                ProximoPeriodoPrediccion nextPeriodPrediction = opt.get();
                npd.setQuantity(nextPeriodPrediction.getCantidad());
                npd.setModel(null);
            }
        }
        ret.setProximoPeriodoDemanda(npd);

        calendar.add(Calendar.MONTH, cantPeriodosAPredecir);
        if(predecirMesActual) calendar.add(Calendar.MONTH, -1);
        Date limite = calendar.getTime();
        calendar.setTime(desde);

        while (calendar.getTime().before(limite)) {
            Integer month = calendar.get(Calendar.MONTH);
            Integer year = calendar.get(Calendar.YEAR);
            Integer demand;
            if(predecirMesActual && currYear.equals(year) && currMonth.equals(month)) {
                demand = null;
            } else {
                demand = historicoDemandaRepository.get(id, family, month + 1, year);
            }

            PeriodoDemandaRealDTO periodoDemandaRealDTO = new PeriodoDemandaRealDTO();
            periodoDemandaRealDTO.setMonth(month + 1);
            periodoDemandaRealDTO.setYear(year);
            periodoDemandaRealDTO.setValue(demand);
            ret.getPeriodos().add(periodoDemandaRealDTO);

            calendar.add(Calendar.MONTH, 1);
        }

        for (PrediccionDemandaModeloDTO model : models) {
            PrediccionDemandaStrategy dps = prediccionDemandaFactory.getStrategy(model.getType());
            PrediccionDemandaDTO prediccion = new PrediccionDemandaDTO();
            prediccion.setId(model.getId());
            prediccion.setType(model.getType());
            prediccion.setNum(model.getNum());
            prediccion.setColor(model.getColor());
            prediccion.setPeriods(dps.predict(ret, model));

            ret.getPredicciones().add(prediccion);
        }
        return ret;
    }

    public void guardarProximoPeriodoDemanda (ProximoPeriodoDemandaDTO dto) throws Exception {
        if (dto.getModel() == null) throw new Exception("No se especificó el modelo");
        Optional<PrediccionDemanda> optionalPrediccionDemanda = prediccionDemandaRepository.findById(dto.getModel());
        if (optionalPrediccionDemanda.isEmpty()) {
            throw new Exception("No se encontró el modelo");
        }
        PrediccionDemanda dpm = optionalPrediccionDemanda.get();

        Optional<ProximoPeriodoPrediccion> next = proximoPeriodoPrediccionRepository.findByArticuloAndAñoAndMonth(dpm.getArticulo(),
                dto.getYear(), dto.getMonth());

        ProximoPeriodoPrediccion npp;
        if (next.isEmpty()) {
            npp = new ProximoPeriodoPrediccion();
            npp.setMes(dto.getMonth());
            npp.setAño(dto.getYear());
            npp.setCantidad(dto.getQuantity());
            npp.setPrediccionDemanda(dpm);
            npp.setArticulo(dpm.getArticulo());
        } else {
            npp = next.get();
            npp.setCantidad(dto.getQuantity());
        }
        proximoPeriodoPrediccionRepository.save(npp);
    }

    public ParametrosDemandaDTO obtenerParametros() {
        ParametrosDemandaDTO ret = new ParametrosDemandaDTO();
        ret.setPeriodosAPredecir(Integer.valueOf(parametrosRepository.findByNombre("PERIODOS_A_PREDECIR").getValor()));
        ret.setMetodoCalculoError(parametrosRepository.findByNombre("METODO_CALCULO_ERROR").getValor());
        ret.setErrorAceptable(Double.valueOf(parametrosRepository.findByNombre("ERROR_ACEPTABLE").getValor()));
        return ret;
    }

    public void guardarParametros(ParametrosDemandaDTO parametrosDemandaDTO) throws Exception {
        Parametros periodosAPredecir = parametrosRepository.findByNombre("PERIODOS_A_PREDECIR");
        Integer intAux = parametrosDemandaDTO.getPeriodosAPredecir();
        if (intAux <= 0) {
            throw new Exception("Se debe predecir al menos un periodo");
        }
        periodosAPredecir.setValor(intAux.toString());
        parametrosRepository.save(periodosAPredecir);

        Parametros metodoCalculoError = parametrosRepository.findByNombre("METODO_CALCULO_ERROR");
        String auxString = parametrosDemandaDTO.getMetodoCalculoError();
        if (!auxString.equals("MAD") && !auxString.equals("MSE") && !auxString.equals("MAPE")) {
            throw new Exception("Método de cálculo de error no soportado");
        }
        metodoCalculoError.setValor(auxString);
        parametrosRepository.save(metodoCalculoError);

        Parametros errorAceptable = parametrosRepository.findByNombre("ERROR_ACEPTABLE");
        Double auxDouble = parametrosDemandaDTO.getErrorAceptable();
        if (auxDouble <= 0) {
            throw new Exception("El error aceptable debe ser mayor a 0");
        }
        errorAceptable.setValor(auxDouble.toString());
        parametrosRepository.save(errorAceptable);
    }
}
