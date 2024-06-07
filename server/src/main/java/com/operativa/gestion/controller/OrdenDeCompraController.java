package com.operativa.gestion.controller;

import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.service.ArticuloService;
import com.operativa.gestion.service.OrdenDeCompraService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrdenDeCompraController {

    private final OrdenDeCompraService ordenDeCompraService;
    private final ArticuloService articuloService;

    public OrdenDeCompraController(OrdenDeCompraService ordenDeCompraService, ArticuloService articuloService) {
        this.ordenDeCompraService = ordenDeCompraService;
        this.articuloService = articuloService;
    }

    @PostMapping("/orden/compra")
    public ResponseEntity<String> crear(@RequestBody OrdenDeCompraDTO ordenDeCompra) {
        List<Long> articulosIds = ordenDeCompra.getArticulos();
        List<Articulo> articulos = new ArrayList<>();
        for (Long articuloId : articulosIds) {
            Optional<Articulo> articulo = articuloService.obtenerArticulo(articuloId);
            if (articulo.isPresent()) {
                articulos.add(articulo.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El artículo con ID " + articuloId + " no existe");
            }
        }
        OrdenDeCompra orden = ordenDeCompraService.crearOrdenDeCompra(ordenDeCompra.getOrdenDeCompra(), articulos);
        return ResponseEntity.status(HttpStatus.CREATED).body("Orden creada correctamente: " + orden);
    }

}