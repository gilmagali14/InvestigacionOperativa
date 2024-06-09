package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.Demanda;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.TipoArticulo;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.repository.OrdenDeCompraRespository;
import com.operativa.gestion.model.repository.TipoArticuloRespository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ArticuloRepository articuloRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final TipoArticuloRespository tipoArticuloRespository;
    private final DemandaService demandaService;


    public ArticuloService(ArticuloRepository articuloRepository, OrdenDeCompraRespository ordenDeCompraRespository, TipoArticuloRespository tipoArticuloRespository,
                           DemandaService demandaService) {
        this.articuloRepository = articuloRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.demandaService = demandaService;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) {

        Articulo articulo = new Articulo(articuloDTO.getNombre(),
                                         articuloDTO.getDescripcion(),
                                         articuloDTO.getTipoArticulo());
        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) throws BadRequestException {
        String query = "SELECT i FROM OrdenArticulo i WHERE i.articulo_id = :articulo_id";
        List<Long> ordenesDeCompras = entityManager.createQuery(query, Long.class)
                                  .setParameter("articulo_id", idArticulo).getResultList();
        for (Long id : ordenesDeCompras) {
            Optional<OrdenDeCompra> ordenDeCompra = ordenDeCompraRespository.findById(id);
            if (ordenDeCompra.get().getEstado().equals("RESOLVED")) {
                    throw new BadRequestException("El artículo posee una orden de compra en curso: " + id);
                }
            }

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


