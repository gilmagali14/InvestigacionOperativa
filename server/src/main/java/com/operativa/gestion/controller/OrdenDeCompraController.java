package com.operativa.gestion.controller;

import com.operativa.gestion.dto.OrdenDeCompraDTO;

import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.service.OrdenDeCompraService;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenDeCompraService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService) {
        this.ordenDeCompraService = ordenDeCompraService;
    }

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenCompraDetalle>> obtenerOrdenes() {
        return ResponseEntity.ok(ordenDeCompraService.obtenerOrdenes());
    }

    @PutMapping("/orden/{idOrden}/{estado}/{proveedor}")
    public ResponseEntity<List<OrdenCompraDetalle>> actualizarOrden(@PathVariable("idOrden") long idOrden,
                                                                    @PathVariable("estado") String estado,
                                                                  @PathVariable("proveedor") String proveedor) {
        return ResponseEntity.ok(ordenDeCompraService.actualizarOrden(idOrden, estado, proveedor));
    }

    @PostMapping("/crear/ordenes")
    public ResponseEntity<OrdenDeCompra> crearOrden(@RequestBody OrdenDeCompraDTO ordenDeCompraDTO) {
        try {
            return ResponseEntity.ok(ordenDeCompraService.crearOrdenes(ordenDeCompraDTO));
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

}