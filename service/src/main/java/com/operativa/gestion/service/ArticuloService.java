package com.operativa.gestion.service;

import com.operativa.gestion.model.ArticuloRepository;
import com.operativa.gestion.model.Articulo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;

    public ArticuloService(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    public void crearArticulo(Articulo articulo) {
        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) {
        articuloRepository.deleteById(idArticulo);
    }

    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }
}
