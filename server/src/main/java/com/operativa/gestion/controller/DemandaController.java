package com.operativa.gestion.controller;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.model.Demanda;
import com.operativa.gestion.service.DemandaService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class DemandaController {

    private final DemandaService demandaService;

    public DemandaController(DemandaService demandaService) {
        this.demandaService = demandaService;
    }
    @PostMapping("/calcular-demanda")
    public ResponseEntity<BigDecimal> calcularDemanda(@RequestBody DemandaDto demanda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.calcularDemanda(demanda));
    }

    @PostMapping("/validar-stock/{id}")
    public ResponseEntity<String> validarStock(@PathVariable("id") long idArticulo) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.validarStock(idArticulo));
    }

    @GetMapping("/demandas")
    public ResponseEntity<List<Demanda>> obtenerDemandas() {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.obtenerDemandas());
    }

    @GetMapping("/calcular-error")
    public ResponseEntity<List<Map<String, Double>>> calcularError() {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.calcularError());
    }
}
