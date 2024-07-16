package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ArticuloOrdenCompraDTO;
import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.service.OrdenDeCompraService;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/crear/orden")
    public ResponseEntity<String> crear(@RequestBody List<ArticuloOrdenCompraDTO> ordenDeCompraDto) {
        try {
            ordenDeCompraService.crearOrdenDeCompra(ordenDeCompraDto);
            return ResponseEntity.ok("Orden creada correctamente");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/ordenes")
    public ResponseEntity<List<OrdenCompraDetalle>> obtenerOrdenes() {
        return ResponseEntity.ok(ordenDeCompraService.obtenerOrdenes());
    }

    @PutMapping("/orden/{idOrden}/{estado}")
    public ResponseEntity<List<OrdenCompraDetalle>> actualizarOrden(@PathVariable("idOrden") long idOrden,
                                                                    @PathVariable("estado") String estado) {
        return ResponseEntity.ok(ordenDeCompraService.actualizarOrden(idOrden, estado));
    }
}