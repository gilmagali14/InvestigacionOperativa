package com.operativa.gestion.service;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DemandaService {

    private final ArticuloRepository articuloRepository;
    private final OrdenDeCompraService ordenDeCompraService;
    private final DemandaRepository demandaRepository;
    private final HistoricoDemandaRepository historicoDemandaRepository;

    public DemandaService(ArticuloRepository articuloRepository, OrdenDeCompraService ordenDeCompraService,
                          DemandaRepository demandaRepository, HistoricoDemandaRepository historicoDemandaRepository) {
        this.articuloRepository = articuloRepository;
        this.ordenDeCompraService = ordenDeCompraService;
        this.demandaRepository = demandaRepository;
        this.historicoDemandaRepository = historicoDemandaRepository;
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


    public String validarStock(Long idArticulo) throws BadRequestException {
        Optional<Articulo> articulo = articuloRepository.findById(idArticulo);
        DemandaDto demandaDto = new DemandaDto();
        demandaDto.setIdArticulo(idArticulo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        demandaDto.setFechaDesde(LocalDateTime.now().minusMonths(3).format(formatter));
        demandaDto.setFechaHasta(LocalDateTime.now().format(formatter));
        //int demanda = calcularDemanda(demandaDto).intValue();

/*        if(articulo.get().getInventario().getStock() <= (demanda)) {

            long cantidadFinal = demanda - articulo.get().getInventario().getStock();
            ArticuloOrdenCompraDTO articuloOrdenCompraDTO = new ArticuloOrdenCompraDTO(idArticulo, (int) cantidadFinal);

            List<ArticuloOrdenCompraDTO> articuloOrdenCompraDTOS = new ArrayList<>();
            articuloOrdenCompraDTOS.add(articuloOrdenCompraDTO);
            ordenDeCompraService.crearOrdenDeCompra(articuloOrdenCompraDTOS);
            return "Orden de compra creada";
        } else {
            return "No hace falta la orden de compra";
        }*/
        return "";
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

}

