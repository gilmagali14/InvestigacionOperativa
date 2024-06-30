package com.operativa.gestion.controller;

import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.Inventario;
import com.operativa.gestion.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping("/inventario/{modelo}")
    public ResponseEntity<List<Inventario>> obtenerInventarioPorModelo(@PathVariable("modelo") String modeloInventario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.obtenerInventarioPorModelo(modeloInventario));
    }

    @PostMapping("/calcular-lote-optimo")
    public ResponseEntity<String> calcularLoteOptimo(@RequestBody LoteOptimoDTO loteOptimoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Calculo demanda: " +
                inventarioService.calcularLoteOptimo(loteOptimoDTO));
    }
}
