package com.operativa.gestion.controller;

import com.operativa.gestion.dto.MostrarOrdenesDTO;
import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.service.OrdenDeCompraService;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }


    @PostMapping("/crear/orden")
    public ResponseEntity<String> crear(@RequestBody OrdenDeCompraDTO ordenDeCompraDto) throws BadRequestException {
        try {
            ordenDeCompraService.crearOrdenDeCompra(ordenDeCompraDto);
            return ResponseEntity.ok("Orden creada correctamente");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/ordenes-de-compra")
    public ResponseEntity<MostrarOrdenesDTO> obtenerOrdenes() {
        return ResponseEntity.ok(ordenDeCompraService.obtenerOrdenes());

    }

}