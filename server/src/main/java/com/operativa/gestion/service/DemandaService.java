package com.operativa.gestion.service;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class DemandaService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;

    public DemandaService(ArticuloRepository articuloRepository,
                          ArticuloVentaRepository articuloVentaRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
    }

    public int getDemandaHistoricaPorArticulo(Articulo articulo) {
        List<ArticuloVenta> list = articuloVentaRepository.findArticuloVentaByArticuloAndAno(articulo, LocalDate.now().getYear());
        int sum = 0;
        for (ArticuloVenta articuloVenta : list) {
            sum += articuloVenta.getCantidadArticulos();
        }
        return sum;
    }

    public int getDemandaHistoricaPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id).get();
        List<ArticuloVenta> list = articuloVentaRepository.findArticuloVentaByArticuloAndAno(articulo, LocalDate.now().getYear());
        int sum = 0;
        for (ArticuloVenta articuloVenta : list) {
            sum += articuloVenta.getCantidadArticulos();
        }
        return sum;
    }

    private LocalDate parseFecha(String fecha) {
        return LocalDate.parse(fecha);
    }

    public List<VentaPeriodoDTO> getDemandaHistoricaPeriodo(HistoricoDemandaDTO historicoDemandaDTO) {
        LocalDate fechaInicio = parseFecha(historicoDemandaDTO.getFechaInicio());
        LocalDate fechaFin = parseFecha(historicoDemandaDTO.getFechaFin());

        List<VentaPeriodoDTO> resultado = new ArrayList<>();

        if (historicoDemandaDTO.getTipoPeriodo().equalsIgnoreCase("1 mes")) {
            LocalDate fechaActual = fechaInicio;
            while (!fechaActual.isAfter(fechaFin)) {
                LocalDate inicioMes = fechaActual.withDayOfMonth(1);
                LocalDate finMes = fechaActual.withDayOfMonth(fechaActual.lengthOfMonth());

                List<ArticuloVenta> ventas = articuloVentaRepository.findArticuloVentaByAnoAndMes(inicioMes.getYear(), inicioMes.getMonthValue());

                VentaPeriodoDTO periodoDTO = new VentaPeriodoDTO();
                periodoDTO.setSalesInPeriod(mapToVentaDTO(ventas));
                periodoDTO.setQuantity(calculateTotalQuantity(periodoDTO.getSalesInPeriod()));
                periodoDTO.setPeriodStart(inicioMes.atStartOfDay().toString());
                periodoDTO.setPeriodEnd(finMes.atTime(23, 59, 59, 999).toString());

                resultado.add(periodoDTO);

                fechaActual = fechaActual.plusMonths(1);
            }
        } else if (historicoDemandaDTO.getTipoPeriodo().equalsIgnoreCase("1 año")) {
            List<ArticuloVenta> ventas = articuloVentaRepository.findArticuloVentaByAno(fechaInicio.getYear());

            VentaPeriodoDTO periodoDTO = new VentaPeriodoDTO();
            periodoDTO.setSalesInPeriod(mapToVentaDTO(ventas));
            periodoDTO.setQuantity(calculateTotalQuantity(periodoDTO.getSalesInPeriod()));
            periodoDTO.setPeriodStart(fechaInicio.withDayOfYear(1).atStartOfDay().toString());
            periodoDTO.setPeriodEnd(fechaFin.withDayOfYear(365).atTime(23, 59, 59, 999).toString());

            resultado.add(periodoDTO);
        }

        return resultado;
    }

    private List<ArticuloVentaDTO> mapToVentaDTO(List<ArticuloVenta> articuloVentas) {
        List<ArticuloVentaDTO> ventaDTOs = new ArrayList<>();
        for (ArticuloVenta av : articuloVentas) {
            ArticuloVentaDTO ventaDTO = new ArticuloVentaDTO();
            ventaDTO.setId(av.getArticulo().getIdArticulo());
            ventaDTO.setQuantity(av.getCantidadArticulos());
            ventaDTOs.add(ventaDTO);
        }
        return ventaDTOs;
    }

    private int calculateTotalQuantity(List<ArticuloVentaDTO> ventas) {
        int total = 0;
        for (ArticuloVentaDTO venta : ventas) {
            total += venta.getQuantity();
        }
        return total;
    }

    public String getMejorMetodo(DemandasDTO dto) {
        Map<Double, String> errores = new HashMap<>();
        errores.put(getPredictionPM(new PromedioMovilDTO(dto.getDemandaHistorica(), dto.getCantidadPeriodos().getCantidadPeriodo(),
                dto.getErrorMetod())).getError(), "Promedio movil");
        errores.put(getPredictionRL(new RegresionLinealDTO(dto.getDemandaHistorica(), dto.getErrorMetod())).getError(),
                "Regresión lineal");
        errores.put(getPredictionPMPE(new PromedioMovilPExpoDTO(dto.getDemandaHistorica(), dto.getErrorMetod(), dto.getAlfa(),
                        dto.getValorInicial())).getError(),
                "Promedio movil ponderado exponencialmente");
        errores.put(getPredictionPMP(new PromedioMovilPonderadoDTO(dto.getDemandaHistorica(), dto.getCantidadPeriodos(), dto.getErrorMetod())).getError(), "Promedio movil ponderado");

        return errores.entrySet()
                .stream()
                .min(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElse(null);
    }


    public ResultadoDemandaDTO getPredictionPMP(PromedioMovilPonderadoDTO dto) {
        List<PrediccionDTO> prediction = new ArrayList<>();
        PeriodosDTO backPeriods = dto.getCantidadPeriodos();
        List<VentaPeriodoDTO> historicalDemand = dto.getDemandaHistorica().subList(
                backPeriods.getCantidadPeriodo(), dto.getDemandaHistorica().size()
        );
        String errorMethod = dto.getErrorMetod();
        int cantidadPeriodos = backPeriods.getCantidadPeriodo();
        List<Integer> predictions = new ArrayList<>();
        List<Integer> real = new ArrayList<>();

        for (int i = 0; i <= historicalDemand.size() - cantidadPeriodos; i++) {
            double sum = 0;
            for (int j = 0; j < cantidadPeriodos; j++) {
                sum += historicalDemand.get(i + j).getQuantity() * backPeriods.getPeso()[j];
            }
            double value = sum / Arrays.stream(backPeriods.getPeso()).sum();
            predictions.add((int) Math.round(value));

            // Ensure to use the period bounds correctly
            prediction.add(new PrediccionDTO(
                    historicalDemand.get(i + cantidadPeriodos - 1).getPeriodStart(),
                    historicalDemand.get(i + cantidadPeriodos - 1).getPeriodEnd(),
                    (int) Math.round(value)
            ));
        }

        real.addAll(historicalDemand.stream()
                .skip(cantidadPeriodos - 1)
                .map(VentaPeriodoDTO::getQuantity)
                .toList());

        // Error calculations
        double error = 0;
        if (errorMethod.equals("MSE")) {
            error = meanSquareError(predictions, real);
        } else if (errorMethod.equals("MAD")) {
            error = averageAbsoluteDeviation(predictions, real);
        } else if (errorMethod.equals("MAPE")) {
            error = meanAbsolutePercentageError(predictions, real);
        }

        // Calculate the prediction for the next period
        double nextPeriod = 0;
        if (historicalDemand.size() >= cantidadPeriodos) {
            int startIdx = historicalDemand.size() - cantidadPeriodos;
            double sum = 0;
            for (int i = 0; i < cantidadPeriodos; i++) {
                int weightIdx = cantidadPeriodos - 1 - i; // reverse the index for weights
                if (weightIdx >= 0 && weightIdx < backPeriods.getPeso().length) {
                    sum += historicalDemand.get(startIdx + i).getQuantity() * backPeriods.getPeso()[weightIdx];
                }
            }
            nextPeriod = sum / Arrays.stream(backPeriods.getPeso()).sum();
        }
        nextPeriod = Math.round(nextPeriod);

        return new ResultadoDemandaDTO(prediction, (int) nextPeriod, error);
    }
    public ResultadoDemandaDTO getPredictionPM(PromedioMovilDTO dto) {
        List<VentaPeriodoDTO> historicalDemand = dto.getDemandaHistorica();
        int backPeriods = dto.getCantidadPeriodos();
        String errorMethod = dto.getErrorMetod();

        if (backPeriods > historicalDemand.size()) {
            throw new IllegalArgumentException("El periodo no puede ser superior al rango de demanda historica");
        }

        List<Integer> predictions = new ArrayList<>();

        for (int i = 0; i < historicalDemand.size(); i++) {
            if (i < backPeriods) {
                predictions.add((int) Math.round(historicalDemand.get(i).getQuantity()));
            } else {
                double sum = 0;
                int count = 0;
                for (int j = i - backPeriods; j < i; j++) {
                    if (j >= 0) {
                        sum += historicalDemand.get(j).getQuantity();
                        count++;
                    }
                }
                double average = count > 0 ? sum / count : 0;
                predictions.add((int) Math.round(average));
            }
        }

        List<Integer> real = new ArrayList<>();
        for (int i = backPeriods; i < historicalDemand.size(); i++) {
            real.add(historicalDemand.get(i).getQuantity());
        }

        List<Integer> predictedValues = predictions.size() > backPeriods
                ? predictions.subList(backPeriods, predictions.size())
                : new ArrayList<>();

        double error = 0;
        if (errorMethod.equals("MSE")) {
            error = meanSquareError(predictedValues, real);
        } else if (errorMethod.equals("MAD")) {
            error = averageAbsoluteDeviation(predictedValues, real);
        } else if (errorMethod.equals("MAPE")) {
            error = meanAbsolutePercentageError(predictedValues, real);
        }

        double nextPeriod = 0;
        for (int i = historicalDemand.size() - backPeriods; i < historicalDemand.size(); i++) {
            nextPeriod += historicalDemand.get(i).getQuantity();
        }
        nextPeriod /= backPeriods;
        nextPeriod = Math.round(nextPeriod);

        List<PrediccionDTO> predictionDTOs = new ArrayList<>();
        for (int i = backPeriods; i < predictions.size(); i++) {
            PrediccionDTO prediccionDTO = new PrediccionDTO();
            prediccionDTO.setPeriodoInicio(historicalDemand.get(i).getPeriodStart());
            prediccionDTO.setPeriodoFin(historicalDemand.get(i).getPeriodEnd());
            prediccionDTO.setValor(predictions.get(i));
            predictionDTOs.add(prediccionDTO);
        }

        ResultadoDemandaDTO predictionResult = new ResultadoDemandaDTO();
        predictionResult.setPrediccion(predictionDTOs);
        predictionResult.setProximoPeriodo((int) nextPeriod);
        predictionResult.setError(error);

        return predictionResult;
    }

    public ResultadoDemandaDTO getPredictionPMPE(PromedioMovilPExpoDTO dto) {
        List<VentaPeriodoDTO> historicalDemand = dto.getDemandaHistorica();
        String errorMethod = dto.getErrorMetod();
        double alfa = dto.getAlfa();
        double initialValue = dto.getValorInicial();

        List<PrediccionDTO> prediction = new ArrayList<>();
        List<Integer> predictions = new ArrayList<>();

        for (VentaPeriodoDTO period : historicalDemand) {
            double predictedValue = initialValue + alfa * (period.getQuantity() - initialValue);
            initialValue = predictedValue;
            predictions.add((int) Math.round(predictedValue));
        }

        List<Integer> real = new ArrayList<>(historicalDemand.stream()
                .skip(1)
                .map(VentaPeriodoDTO::getQuantity)
                .toList());
        if (predictions.size() > 1) {
            predictions = predictions.subList(0, predictions.size() - 1);
        }

        double error = 0;
        if ("MSE".equals(errorMethod)) {
            error = meanSquareError(predictions, real);
        } else if ("MAD".equals(errorMethod)) {
            error = averageAbsoluteDeviation(predictions, real);
        } else if ("MAPE".equals(errorMethod)) {
            error = meanAbsolutePercentageError(predictions, real);
        }

        int nextPeriod = !predictions.isEmpty() ? predictions.get(predictions.size() - 1) : 0;

        for (int i = 0; i < predictions.size(); i++) {
            VentaPeriodoDTO period = historicalDemand.get(i + 1);
            prediction.add(new PrediccionDTO(
                    period.getPeriodStart(),
                    period.getPeriodEnd(),
                    predictions.get(i)
            ));
        }

        return new ResultadoDemandaDTO(prediction, nextPeriod, error);
    }

    public ResultadoDemandaDTO getPredictionRL(RegresionLinealDTO dto) {
        List<VentaPeriodoDTO> historicalDemand = dto.getDemandaHistorica();
        String errorMethod = dto.getErrorMetod();
        int n = historicalDemand.size();
        double sumX = 0;
        double sumXY = 0;
        double sumX2 = 0;
        double promX = 0;
        double promY = 0;

        for (int i = 0; i < n; i++) {
            int x = i + 1;
            double y = historicalDemand.get(i).getQuantity();
            sumX += x;
            sumXY += x * y;
            sumX2 += x * x;
            promX += x;
            promY += y;
        }

        promX /= n;
        promY /= n;

        double b = (sumXY - n * promX * promY) / (sumX2 - n * promX * promX);
        double a = promY - b * promX;

        List<PrediccionDTO> prediction = new ArrayList<>();
        List<Integer> predictions = new ArrayList<>();
        List<Integer> real = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int x = i + 1;
            int predictedValue = (int) Math.round(a + b * x);
            predictions.add(predictedValue);
            real.add(historicalDemand.get(i).getQuantity());

            // Create prediction DTO
            PrediccionDTO predDTO = new PrediccionDTO(
                    historicalDemand.get(i).getPeriodStart(),
                    historicalDemand.get(i).getPeriodEnd(),
                    predictedValue
            );
            prediction.add(predDTO);
        }
        double error = 0;
        if ("MSE".equals(errorMethod)) {
            error = meanSquareError(predictions, real);
        } else if ("MAD".equals(errorMethod)) {
            error = averageAbsoluteDeviation(predictions, real);
        } else if ("MAPE".equals(errorMethod)) {
            error = meanAbsolutePercentageError(predictions, real);
        }
        double nextPeriod = a + b * (n + 1);
        nextPeriod = Math.round(nextPeriod);
        return new ResultadoDemandaDTO(prediction, (int) nextPeriod, error);
    }

    private double meanSquareError(List<Integer> predictions, List<Integer> real) {
        if (predictions.size() != real.size()) {
            return 0.0;
        }

        int n = predictions.size();
        double sum = 0;

        for (int i = 0; i < n; i++) {
            int prediction = predictions.get(i);
            int actual = real.get(i);
            double squaredError = Math.pow(actual - prediction, 2);
            sum += squaredError;
        }

        return sum / n;
    }

    private double averageAbsoluteDeviation(List<Integer> predictions, List<Integer> real) {
        if (predictions.size() != real.size()) {
            return 0.0;
        }

        int n = predictions.size();
        double sum = 0;

        for (int i = 0; i < n; i++) {
            int prediction = predictions.get(i);
            int actual = real.get(i);
            double absoluteDeviation = Math.abs(actual - prediction);
            sum += absoluteDeviation;
        }

        return sum / n;
    }

    private double meanAbsolutePercentageError(List<Integer> predictions, List<Integer> real) {
        if (predictions.size() != real.size()) {
            return 0.0;
        }

        int n = predictions.size();
        double sum = 0;

        for (int i = 0; i < n; i++) {
            int prediction = predictions.get(i);
            int actual = real.get(i);

            if (actual == 0 && prediction == 0) {
                sum += 0;
            } else if (actual == 0) {
                sum += Double.POSITIVE_INFINITY; // Actual is zero, consider infinite error
            } else {
                double absolutePercentageError = Math.abs((actual - prediction) / (double) actual);
                sum += absolutePercentageError;
            }
        }

        // Calculate MAPE as a percentage
        double mape = (sum / n) * 100;
        return mape;
    }
}
