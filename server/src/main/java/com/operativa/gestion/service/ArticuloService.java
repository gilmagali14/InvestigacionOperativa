package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.TipoArticulo;
import com.operativa.gestion.model.repository.*;
import com.operativa.gestion.model.Articulo;
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

    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;

    public ArticuloService(ArticuloRepository articuloRepository, TipoArticuloRespository tipoArticuloRespository, ProveedorRepository proveedorRepository, OrdenDeCompraRespository ordenDeCompraRespository,
                           OrdenCompraDetalleRepository ordenCompraDetalleRepository) {
        this.articuloRepository = articuloRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.proveedorRepository = proveedorRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) throws BadRequestException {
        Optional<TipoArticulo> tipoArticulo = tipoArticuloRespository.findByNombre(articuloDTO.getNombreTipoArticulo());
        Optional<Proveedor> proveedor = proveedorRepository.findByNombre(articuloDTO.getNombreProveedor());

        if (tipoArticulo.isEmpty() || proveedor.isEmpty()) {
            throw new BadRequestException("El tipoArticulo o proveedor no existe");
        }

        Articulo articulo = new Articulo(articuloDTO.getNombre(), articuloDTO.getDescripcion(),
                                         articuloDTO.getPrecio(), tipoArticulo.get(), proveedor.get(),
                articuloDTO.getCostoAlmacenamiento());
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


