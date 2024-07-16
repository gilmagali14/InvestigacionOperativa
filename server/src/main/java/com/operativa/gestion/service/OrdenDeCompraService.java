package com.operativa.gestion.service;

import com.operativa.gestion.EstadosOrdenDeCompra;
import com.operativa.gestion.dto.VentasDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenDeCompraService {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final DemandaRepository demandaRepository;
    private final ArticuloRepository articuloRepository;

    public OrdenDeCompraService(OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                                OrdenDeCompraRespository ordenDeCompraRespository,
                                DemandaRepository demandaRepository, ArticuloRepository articuloRepository) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.demandaRepository = demandaRepository;
        this.articuloRepository = articuloRepository;
    }

    public OrdenDeCompra crearOrden(Articulo articulo, Double loteOptimo) throws BadRequestException {
        List<OrdenCompraDetalle> ordenCompraDetalle = ordenCompraDetalleRepository.findByArticulo(articulo);
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

    public List<OrdenCompraDetalle> actualizarOrden(Long idOrden, String estadoActualizar) {
        OrdenCompraDetalle ordenDeCompra = ordenCompraDetalleRepository.findById(idOrden).get();
        EstadoOrdenDeCompra estadoOrdenDeCompra = ordenDeCompra.getOrdenDeCompra().getEstadoOrdenDeCompra();
        estadoOrdenDeCompra.setNombreEstadoOrdenDeCompra(estadoActualizar);

        if(estadoActualizar.equals(EstadosOrdenDeCompra.ACEPTADA.name())) {
            Articulo articulo = ordenDeCompra.getArticulo();
            articulo.setStock(articulo.getStock() + ordenDeCompra.getCantidad());
            articuloRepository.save(articulo);
        }
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        return ordenCompraDetalleRepository.findAll();
    }

}
