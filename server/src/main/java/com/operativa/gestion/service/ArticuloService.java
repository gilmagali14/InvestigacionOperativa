package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.TipoArticulo;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.repository.TipoArticuloRespository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final TipoArticuloRespository tipoArticuloRespository;

    public ArticuloService(ArticuloRepository articuloRepository, TipoArticuloRespository tipoArticuloRespository) {
        this.articuloRepository = articuloRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) {

        Articulo articulo = new Articulo(articuloDTO.getNombre(),
                                         articuloDTO.getDescripcion(),
                                         articuloDTO.getTipoArticulo());
        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) {
        articuloRepository.deleteById(idArticulo);
    }

    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerArticulo(Long idArticulo) {
        return articuloRepository.findById(idArticulo);
    }

    public List<TipoArticulo> obtenerTipoArticulos() {
        return tipoArticuloRespository.findAll();
    }
}


