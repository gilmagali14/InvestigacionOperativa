package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.repository.OrdenCompraDetalleRepository;
import com.operativa.gestion.model.repository.OrdenDeCompraRespository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ArticuloRepository articuloRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    public ArticuloService(ArticuloRepository articuloRepository, OrdenDeCompraRespository ordenDeCompraRespository,
                           OrdenCompraDetalleRepository ordenCompraDetalleRepository) {
        this.articuloRepository = articuloRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) {

        Articulo articulo = new Articulo(articuloDTO.getNombre(),
                                         articuloDTO.getDescripcion(),
                                         articuloDTO.getTipoArticulo(),
                                         articuloDTO.getProveedor(),
                                         articuloDTO.getNumeroLote());
        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) throws BadRequestException {
      /*   List<String> ordenesDeCompras = ordenCompraDetalleRepository.findArticuloIds(idArticulo);
        for (String estado: ordenesDeCompras) {
            if (!estado.equals("RESOLVED")) {
                throw new BadRequestException("El artículo posee una orden de compra en curso: " + idArticulo);
            }
        }*/
        Articulo updated = articuloRepository.findById(idArticulo).get();
        updated.setFechaBaja(LocalDateTime.now());
        articuloRepository.save(updated);
    }

    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerArticulo(Long idArticulo) {
        return articuloRepository.findById(idArticulo);
    }
}


