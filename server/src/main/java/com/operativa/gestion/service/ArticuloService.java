package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @PersistenceContext
    private EntityManager entityManager;

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final ArticuloRepository articuloRepository;
    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;
    private final InventarioRepository inventarioRepository;

    public ArticuloService(OrdenCompraDetalleRepository ordenCompraDetalleRepository, ArticuloRepository articuloRepository, TipoArticuloRespository tipoArticuloRespository,
                           ProveedorRepository proveedorRepository, InventarioRepository inventarioRepository) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.articuloRepository = articuloRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.proveedorRepository = proveedorRepository;
        this.inventarioRepository = inventarioRepository;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) throws BadRequestException {
        Optional<TipoArticulo> tipoArticulo = tipoArticuloRespository.findByNombre(articuloDTO.getNombreTipoArticulo());
        Optional<Proveedor> proveedor = proveedorRepository.findByNombre(articuloDTO.getNombreProveedor());

        if (tipoArticulo.isEmpty() || proveedor.isEmpty()) {
            throw new BadRequestException("El tipo de articulo o proveedor no existe");
        }

        Articulo articulo = new Articulo(articuloDTO.getNombre(), articuloDTO.getDescripcion(),
                                         articuloDTO.getPrecio(), tipoArticulo.get(), proveedor.get(),
                                         articuloDTO.getCostoAlmacenamiento());

        if(articuloDTO.getStock() < articuloDTO.getStockSeguridad()) {
            throw new BadRequestException("El stock no puede ser menor que el stock de seguridad");
        }
        Inventario inventario = new Inventario(articulo.getCodArticulo(), articuloDTO.getStockSeguridad(),
                                                articuloDTO.getStock(), articuloDTO.getModelo());
        inventarioRepository.save(inventario);
        articulo.setInventario(inventario);

        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) throws BadRequestException {
        List<OrdenCompraDetalle> ordenesDeCompras = ordenCompraDetalleRepository.findByArticuloId(idArticulo);
        if(!ordenesDeCompras.isEmpty()) {
            for (OrdenCompraDetalle orden : ordenesDeCompras) {
                String estado = orden.getOrdenDeCompra().getEstadoOrdenDeCompra().getNombreEstadoOrdenDeCompra();
                if (!estado.equals("RESOLVED")) {
                    throw new BadRequestException("El artículo posee una orden de compra en curso: " + idArticulo);
                }
            }
        }
        Optional<Articulo> articulo = articuloRepository.findById(idArticulo);
        Articulo art = articulo.get();
        art.setFechaBaja(LocalDateTime.now());
        articuloRepository.save(art);
    }

    public void actualizarArticulo(ArticuloDTO articuloDTO) throws BadRequestException {
        Optional<Articulo> articulo = articuloRepository.findById(articuloDTO.getId());
        if (articulo.isEmpty()) {
            throw new BadRequestException("El articulo no existe");
        }
        Optional<TipoArticulo> tipoArticulo = tipoArticuloRespository.findByNombre(articuloDTO.getNombreTipoArticulo());
        Optional<Proveedor> proveedor = proveedorRepository.findByNombre(articuloDTO.getNombreProveedor());

        if (tipoArticulo.isEmpty() || proveedor.isEmpty()) {
            throw new BadRequestException("El tipo de articulo o proveedor no existe");
        }

        Articulo art = articulo.get();
        art.setNombre(articuloDTO.getNombre());
        art.setDescripcion(articuloDTO.getDescripcion());
        art.setPrecio(articuloDTO.getPrecio());
        art.setTipoArticulo(tipoArticulo.get());
        art.setProveedor(proveedor.get());
        art.setCostoAlmacenamiento(articuloDTO.getCostoAlmacenamiento());

        articuloRepository.save(art);

        Inventario inventario = inventarioRepository.findByIdArticulo(articuloDTO.getId());
        inventario.setStock(articuloDTO.getStock());
        inventario.setStockSeguridad(articuloDTO.getStockSeguridad());
        inventario.setModelo(articuloDTO.getModelo());
    }
    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerArticulo(Long idArticulo) {
        return articuloRepository.findById(idArticulo);
    }
}


