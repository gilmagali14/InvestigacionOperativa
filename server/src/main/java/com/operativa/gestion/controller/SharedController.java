package com.operativa.gestion.controller;

import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.TipoArticulo;

import com.operativa.gestion.service.SharedService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SharedController {

    private final SharedService service;

    public SharedController(SharedService service) {
        this.service = service;
    }

    @PostMapping("/create/tipo-articulo")
    public ResponseEntity<String> crearTipoArticulo(@RequestBody TipoArticulo tipoArticulo) {
        service.crearTipoArticulo(tipoArticulo);
        return ResponseEntity.status(HttpStatus.CREATED).body("TipoArticulo creado correctamente");
    }

    @PostMapping("/create/proveedor")
    public ResponseEntity<String> crearProveedor(@RequestBody Proveedor proveedor) {
        service.crearProveedor(proveedor);
        return ResponseEntity.status(HttpStatus.CREATED).body("Proveedor creado correctamente");
    }

    @GetMapping("/tipo-articulos")
    public ResponseEntity<List<TipoArticulo>> obtenerTipoArticulos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obtenerTipoArticulos());
    }

    @GetMapping("/proveedores")
    public ResponseEntity<List<Proveedor>> obtenerProveedores() {
        return ResponseEntity.status(HttpStatus.OK).body(service.obtenerProveedores());
    }
}
