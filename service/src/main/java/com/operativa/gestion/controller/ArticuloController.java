package com.operativa.gestion.controller;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.service.ArticuloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@org.springframework.stereotype.Controller
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @PostMapping("/create/articulo")
    public ResponseEntity<String> crearArticulo(@RequestBody Articulo articulo) {
        articuloService.crearArticulo(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Articulo creado correctamente");
    }

    @DeleteMapping("/delete/articulo/{id}")
    public ResponseEntity<String> borrarArticulo(@PathVariable("id") long idArticulo) {
        articuloService.borrarArticulo(idArticulo);
        return ResponseEntity.status(HttpStatus.OK).body("Articulo borrado correctamente: " + idArticulo);
    }

    @GetMapping("/articulos")
    public ResponseEntity<List<Articulo>> obtenerArticulos() {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.obtenerArticulos());
    }
}
