package com.operativa.gestion.controller;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.TipoArticulo;
import com.operativa.gestion.service.ArticuloService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ArticuloController {

    private final ArticuloService articuloService;

    public ArticuloController(ArticuloService articuloService) {
        this.articuloService = articuloService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create/articulo")
    public ResponseEntity<String> crearArticulo(@RequestBody ArticuloDTO articulo) {
        articuloService.crearArticulo(articulo);
        return ResponseEntity.status(HttpStatus.CREATED).body("Articulo creado correctamente");
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/articulo/{id}")
    public ResponseEntity<String> borrarArticulo(@PathVariable("id") long idArticulo) throws BadRequestException {
        articuloService.borrarArticulo(idArticulo);
        return ResponseEntity.status(HttpStatus.OK).body("Articulo borrado correctamente: " + idArticulo);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/articulos")
    public ResponseEntity<List<Articulo>> obtenerArticulos() {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.obtenerArticulos());
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tipo_articulos")
    public ResponseEntity<List<TipoArticulo>> obtenerTipoArticulos() {
        return ResponseEntity.status(HttpStatus.OK).body(articuloService.obtenerTipoArticulos());
    }
}
