package com.operativa.gestion.service;

import com.operativa.gestion.EstadosOrdenDeCompra;
import com.operativa.gestion.dto.ArticuloOrdenCompraDTO;
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
    private final InventarioRepository inventarioRepository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final DemandaRepository demandaRepository;
    private final ArticuloRepository articuloRepository;

    public OrdenDeCompraService(OrdenCompraDetalleRepository ordenCompraDetalleRepository,
                                EstadoOrdenCompraRepository estadoOrdenCompraRepository,
                                InventarioRepository inventarioRepository,
                                OrdenDeCompraRespository ordenDeCompraRespository,
                                DemandaRepository demandaRepository, ArticuloRepository articuloRepository) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.inventarioRepository = inventarioRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.demandaRepository = demandaRepository;
        this.articuloRepository = articuloRepository;
    }

    public OrdenDeCompra crearOrdenDeCompra(List<ArticuloOrdenCompraDTO> articuloOrdenCompraDTO) throws BadRequestException {
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        EstadoOrdenDeCompra estadoOrdenDeCompra = new EstadoOrdenDeCompra(EstadosOrdenDeCompra.PENDIENTE.name());
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        ordenDeCompra.setEstadoOrdenDeCompra(estadoOrdenDeCompra);
        List<OrdenCompraDetalle> ordenCompra = new ArrayList<>();

        for (ArticuloOrdenCompraDTO x : articuloOrdenCompraDTO) {
           Articulo articulo = articuloRepository.findById(x.getIdArticulo()).get();
           OrdenCompraDetalle ordenCompraDetalle = new OrdenCompraDetalle(ordenDeCompra, articulo, x.getCantidad());
           ordenCompra.add(ordenCompraDetalle);

        }
        ordenDeCompraRespository.save(ordenDeCompra);
        ordenCompraDetalleRepository.saveAll(ordenCompra);
        return ordenDeCompra;
    }

    public List<OrdenCompraDetalle> obtenerOrdenes() {
        return ordenCompraDetalleRepository.findAll();
    }

    public List<OrdenCompraDetalle> actualizarOrden(Long idOrden, String estadoActualizar) {
        Optional<OrdenCompraDetalle> ordenDeCompra = ordenCompraDetalleRepository.findById(idOrden);
        OrdenCompraDetalle detalle = ordenDeCompra.get();
        EstadoOrdenDeCompra estadoOrdenDeCompra = detalle.getOrdenDeCompra().getEstadoOrdenDeCompra();
        estadoOrdenDeCompra.setNombreEstadoOrdenDeCompra(estadoActualizar);
        Articulo articulo = detalle.getArticulo();

        if(estadoActualizar.equals(EstadosOrdenDeCompra.ACEPTADA.name())) {
            Inventario inventario = articulo.getInventario();
            long cantidad = inventario.getStock() + detalle.getCantidad();
            inventario.setStock(cantidad);
            inventarioRepository.save(inventario);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate primerDiaDelMes = LocalDate.now().withDayOfMonth(1);
            LocalDate ultimoDiaDelMes = LocalDate.now().withDayOfMonth(30);
            Demanda demanda = new Demanda(primerDiaDelMes.format(formatter), ultimoDiaDelMes.format(formatter),
                    0, (int) cantidad, articulo.getCodArticulo(), "lote-fijo");
            demandaRepository.save(demanda);
        }
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        return ordenCompraDetalleRepository.findAll();
    }

}
