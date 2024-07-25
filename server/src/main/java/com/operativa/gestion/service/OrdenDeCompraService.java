package com.operativa.gestion.service;

import com.operativa.gestion.EstadosOrdenDeCompra;
import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenDeCompraService {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final ArticuloRepository articuloRepository;
    private final ProveedorRepository proveedorRepository;
    private final ArticuloProveedorRepository articuloProveedorRepository;
    private final InventarioService inventarioService;

    public OrdenDeCompraService(OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                                OrdenDeCompraRespository ordenDeCompraRespository,
                                ArticuloRepository articuloRepository,
                                ProveedorRepository proveedorRepository,
                                ArticuloProveedorRepository articuloProveedorRepository, InventarioService inventarioService) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.articuloRepository = articuloRepository;
        this.proveedorRepository = proveedorRepository;
        this.articuloProveedorRepository = articuloProveedorRepository;
        this.inventarioService = inventarioService;
    }

    public OrdenDeCompra crearOrdenes(OrdenDeCompraDTO orden) throws BadRequestException {
        List<OrdenCompraDetalle> o = ordenCompraDetalleRepository.findByArticuloIdAndStatus(orden.getIdArticulo());
        if (o.size() > 0) {
            throw new BadRequestException("Ya existe una orden de compra para ese articulo pendiente");
        }

        Articulo articulo = articuloRepository.findById(orden.getIdArticulo()).get();
        Proveedor proveedor = proveedorRepository.findByNombre(orden.getProveedor()).get();
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloAndProveedor(articulo, proveedor).get();
        OrdenDeCompra ord = crearOrden(articuloProveedor, orden.getCantidad());
        return ord;
    }

    public OrdenDeCompra crearOrden(ArticuloProveedor articulo, Double loteOptimo) throws BadRequestException {
        List<OrdenCompraDetalle> ordenCompraDetalle = ordenCompraDetalleRepository.findByArticuloProveedor(articulo);
        if(!ordenCompraDetalle.isEmpty()) {
            for (OrdenCompraDetalle orden : ordenCompraDetalle) {
                if(orden.getOrdenDeCompra().getEstadoOrdenDeCompra().getNombreEstadoOrdenDeCompra()
                        .equals(EstadosOrdenDeCompra.PENDIENTE.toString())) {
                    throw new BadRequestException("Hay una orden de compra pendiente");
                }
            }
        }
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        EstadoOrdenDeCompra estadoOrdenDeCompra = new EstadoOrdenDeCompra(EstadosOrdenDeCompra.PENDIENTE.name());
        ordenDeCompra.setEstadoOrdenDeCompra(estadoOrdenDeCompra);
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        ordenDeCompraRespository.save(ordenDeCompra);
        ordenCompraDetalleRepository.save(new OrdenCompraDetalle(ordenDeCompra, articulo, loteOptimo.intValue()));
        return ordenDeCompra;
    }

    public List<OrdenCompraDetalle> obtenerOrdenes() {
        return ordenCompraDetalleRepository.findAll();
    }

    public List<OrdenCompraDetalle> actualizarOrden(Long idOrden, String estadoActualizar, String proveedor) {
        OrdenCompraDetalle ordenDeCompra = ordenCompraDetalleRepository.findById(idOrden).get();
        EstadoOrdenDeCompra estadoOrdenDeCompra = ordenDeCompra.getOrdenDeCompra().getEstadoOrdenDeCompra();
        estadoOrdenDeCompra.setNombreEstadoOrdenDeCompra(estadoActualizar);
        ArticuloProveedor articuloProveedor = ordenDeCompra.getArticuloProveedor();
        if(estadoActualizar.equals(EstadosOrdenDeCompra.ACEPTADA.name())) {
            Articulo articulo = articuloProveedor.getArticulo();
            articulo.setStock(articulo.getStock() + ordenDeCompra.getCantidad());
            articuloRepository.save(articulo);

            articuloProveedor = inventarioService.updateArticuloInventario(articuloProveedor);
            articuloProveedorRepository.save(articuloProveedor);
        }
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        return ordenCompraDetalleRepository.findAll();
    }

}
