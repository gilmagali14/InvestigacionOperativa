package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.service.ArticuloService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @PostMapping("/crear/articulo")
    public ResponseEntity<String> crearArticulo(@RequestBody ArticuloDTO articulo) throws BadRequestException {
        articuloService.crearArticulo(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Articulo creado correctamente");
    }

    @DeleteMapping("/baja/articulo/{id}")
    public ResponseEntity<String> borrarArticulo(@PathVariable("id") long idArticulo) {
        try {
            articuloService.borrarArticulo(idArticulo);
            return ResponseEntity.ok("Artículo borrado correctamente");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/actualizar/articulo")
    public ResponseEntity<String> actualizarArticulo(@RequestBody ArticuloDTO articulo) {
        try {
            articuloService.actualizarArticulo(articulo);
            return ResponseEntity.ok("Artículo actualizado correctamente");
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/articulos")
    public ResponseEntity<List<Articulo>> obtenerArticulos() {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.obtenerArticulos());
    }
}
