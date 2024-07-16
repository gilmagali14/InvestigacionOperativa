package com.operativa.gestion.controller;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping("/inventario/{modelo}/{tipo-articulo}")
    public ResponseEntity<List<Articulo>> obtenerInventarioPorModelo(@PathVariable("modelo") String modeloInventario,
                                                                     @PathVariable("tipo-articulo") String tipoArticulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.obtenerInventarioPorModelo(modeloInventario,
                                                                                                           tipoArticulo));
    }

    @PostMapping("/inventario/lote-optimo")
    public ResponseEntity<BigDecimal> calcularLoteOptimo(@RequestBody Articulo articulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.calcularLoteOptimo(articulo));
    }

    @PostMapping("/inventario/punto-pedido")
    public ResponseEntity<BigDecimal> calcularPuntoPedido(@RequestBody Articulo articulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.calcularPuntoPedido(articulo));
    }

    @PostMapping("/inventario/cgi")
    public ResponseEntity<BigDecimal> calcularCgi(@RequestBody Articulo articulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.calcularCgi(articulo));
    }

    @GetMapping("/inventario/{modelo}/{tipo-articulo}/articulos-reponer")
    public ResponseEntity<List<Articulo>> articulosReponer(@PathVariable("modelo") String modeloInventario,
                                                          @PathVariable("tipo-articulo") String tipoArticulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.articulosReponer(modeloInventario, tipoArticulo));
    }

    @GetMapping("/inventario/{modelo}/{tipo-articulo}/articulos-faltantes")
    public ResponseEntity<List<Articulo>> articulosFaltantes(@PathVariable("modelo") String modeloInventario,
                                                             @PathVariable("tipo-articulo") String tipoArticulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.articulosFaltantes(modeloInventario, tipoArticulo));
    }
}
