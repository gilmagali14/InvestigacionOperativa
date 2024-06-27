package com.operativa.gestion.controller;

import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.service.OrdenDeCompraService;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/crear/orden")
    public ResponseEntity<String> crear(@RequestBody OrdenDeCompraDTO ordenDeCompraDto) throws BadRequestException {
        try {
            ordenDeCompraService.crearOrdenDeCompra(ordenDeCompraDto);
            return ResponseEntity.ok("Orden creada correctamente");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}