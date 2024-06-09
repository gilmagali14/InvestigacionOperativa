package com.operativa.gestion.service;

import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.repository.OrdenDeCompraRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdenDeCompraService {

    private final OrdenDeCompraRespository ordenDeCompraRespository;

    public OrdenDeCompraService(OrdenDeCompraRespository ordenDeCompraRespository) {
        this.ordenDeCompraRespository = ordenDeCompraRespository;
    }

    public OrdenDeCompra crearOrdenDeCompra(OrdenDeCompra ordenDeCompra, List<Articulo> articulos) {
        ordenDeCompra.setArticulos(articulos);
        return ordenDeCompraRespository.save(ordenDeCompra);
    }
}
