package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.Demanda;
import com.operativa.gestion.service.DemandaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DemandaController {

    private DemandaService demandaService;


    @PostMapping("/create/demanda")
    public ResponseEntity<String> crearDemanda(@RequestBody Demanda demanda) {
        //demandaService.crearDemanda(demanda);
        return ResponseEntity.status(HttpStatus.CREATED).body("Demanda creado correctamente");
    }






}
