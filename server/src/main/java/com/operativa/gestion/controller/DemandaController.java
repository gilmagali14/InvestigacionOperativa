package com.operativa.gestion.controller;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.service.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class DemandaController {

    @Autowired
    DemandaService demandaService;

    @GetMapping("/demanda-historica/{id}")
    public ResponseEntity<Integer> obtenerDemandas(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.getDemandaHistoricaPorId(id));
    }

    @PostMapping("/demanda-historica")
    public ResponseEntity<List<VentaPeriodoDTO>> obtenerDemandaPorPeriodo(@RequestBody HistoricoDemandaDTO historicoDemandaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.getDemandaHistoricaPeriodo(historicoDemandaDTO));
    }

    @PostMapping(path = "/promedio-movil")
    public ResponseEntity<ResultadoDemandaDTO> getPromedioMovil(@RequestBody PromedioMovilDTO dto) {
        return ResponseEntity.ok(demandaService.getPredictionPM(dto));
    }

    @PostMapping(path = "/promedio-movil-ponderado")
    public ResponseEntity<ResultadoDemandaDTO> getPromedioMovilPonderado(@RequestBody PromedioMovilPonderadoDTO dto) {
        return ResponseEntity.ok(demandaService.getPredictionPMP(dto));
    }

    @PostMapping(path = "/promedio-movil-ponderado-exp")
    public ResponseEntity<ResultadoDemandaDTO> getPromedioMovilPonderadoExp(@RequestBody PromedioMovilPExpoDTO dto) {
        return ResponseEntity.ok(demandaService.getPredictionPMPE(dto));
    }

    @PostMapping(path = "/regresion-lineal")
    public ResponseEntity<ResultadoDemandaDTO> getPromedioMovilPonderadoExp(@RequestBody RegresionLinealDTO dto) {
        return ResponseEntity.ok(demandaService.getPredictionRL(dto));
    }

    @PostMapping(path = "/mejor-metodo")
    public ResponseEntity<String> getMejorMetodo(@RequestBody DemandasDTO dto) {
        return ResponseEntity.ok(demandaService.getMejorMetodo(dto));
    }
}
