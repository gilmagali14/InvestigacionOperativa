package com.operativa.gestion.controller;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.service.DemandaService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

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


    @PostMapping("/calcular-punto-pedido")
    public ResponseEntity<String> calcularPuntoPedio(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularPuntoPedido(loteOptimoDTO));
    }

    @PostMapping("/calcular-cgi")
    public ResponseEntity<String> calcularCgi(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                demandaService.calcularCgi(loteOptimoDTO));
    }

    @PostMapping("/validar-stock/{id}")
    public ResponseEntity<String> validarStock(@PathVariable("id") long idArticulo) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(demandaService.validarStock(idArticulo));
    }
}
