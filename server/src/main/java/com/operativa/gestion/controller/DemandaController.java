package com.operativa.gestion.controller;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.service.DemandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DemandaController {

    private final DemandaService demandaService;

    public DemandaController(DemandaService demandaService) {
        this.demandaService = demandaService;
    }

    @PostMapping("/calcular-demanda")
    public ResponseEntity<String> calcularDemanda(@RequestBody DemandaDto demanda) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularDemanda(demanda));
    }

    @PostMapping("/calcular-lote-optimo")
    public ResponseEntity<String> calcularLoteOptimo(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularLoteOptimo(loteOptimoDTO));
    }

    @PostMapping("/calcular-punto-pedido")
    public ResponseEntity<String> calcularPuntoPedio(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularPuntoPedido(loteOptimoDTO));
    }

    @PostMapping("/calcular-cgi")
    public ResponseEntity<String> calcularCgi(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularLoteOptimo(loteOptimoDTO));
    }
}
