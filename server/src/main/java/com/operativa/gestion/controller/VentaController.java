package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ArticuloOrdenCompraDTO;
import com.operativa.gestion.dto.VentaDTO;
import com.operativa.gestion.dto.VentasDTO;
import com.operativa.gestion.model.ArticuloVenta;
import com.operativa.gestion.service.VentaService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping("/crear/venta")
    public ResponseEntity<List<ArticuloOrdenCompraDTO>> crearVenta(@RequestBody VentaDTO venta) throws BadRequestException {
        List<ArticuloOrdenCompraDTO> articulos = ventaService.crearVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(articulos);
    }

    @GetMapping("/ventas/{idArticulo}")
    public ResponseEntity<List<VentasDTO>> mostrarVentasPorArticulo(@PathVariable Long idArticulo) {
        return ResponseEntity.status(HttpStatus.CREATED).body( ventaService.mostrarVentasPorArticulo(idArticulo));
    }

    @GetMapping("/ventas")
    public ResponseEntity<List<ArticuloVenta>> mostrarVentas() {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.mostrarVentas());
    }

}
