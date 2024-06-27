package com.operativa.gestion.service;

import com.operativa.gestion.EstadosOrdenDeCompra;
import com.operativa.gestion.dto.ArticuloOrdenCompraDTO;
import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.EstadoOrdenDeCompra;
import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.repository.EstadoOrdenCompraRepository;
import com.operativa.gestion.model.repository.OrdenCompraDetalleRepository;
import com.operativa.gestion.model.repository.OrdenDeCompraRespository;
import com.operativa.gestion.model.repository.TipoArticuloRespository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenDeCompraService {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final EstadoOrdenCompraRepository estadoOrdenCompraRepository;
    private final TipoArticuloRespository tipoArticuloRespository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final ArticuloService articuloService;

    public OrdenDeCompraService(OrdenCompraDetalleRepository ordenCompraDetalleRepository, EstadoOrdenCompraRepository estadoOrdenCompraRepository, TipoArticuloRespository tipoArticuloRespository,
                                OrdenDeCompraRespository ordenDeCompraRespository,
                                ArticuloService articuloService) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.estadoOrdenCompraRepository = estadoOrdenCompraRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.articuloService = articuloService;
    }

    public void crearOrdenDeCompra(OrdenDeCompraDTO ordenDeCompraDto) throws BadRequestException {
        List<ArticuloOrdenCompraDTO> articulosIds = ordenDeCompraDto.getArticulos();
        OrdenDeCompra ordenDeCompra = new OrdenDeCompra();
        EstadoOrdenDeCompra estadoOrdenDeCompra = new EstadoOrdenDeCompra(EstadosOrdenDeCompra.CREADA.name());
        estadoOrdenCompraRepository.save(estadoOrdenDeCompra);
        ordenDeCompra.setEstadoOrdenDeCompra(estadoOrdenDeCompra);
        List<OrdenCompraDetalle> ordenCompra = new ArrayList<>();
        List<Long> articulosNoPresentes = new ArrayList<>();

        for (ArticuloOrdenCompraDTO x : articulosIds) {
            Optional<Articulo> articuloOptional = articuloService.obtenerArticulo(x.getIdArticulo());
            if (articuloOptional.isPresent()) {
                Articulo articulo = articuloOptional.get();
                OrdenCompraDetalle ordenCompraDetalle = new OrdenCompraDetalle(ordenDeCompra, articulo, x.getCantidad());
                ordenCompra.add(ordenCompraDetalle);
            } else {
                articulosNoPresentes.add(x.getIdArticulo());
            }
        }

        if (!articulosNoPresentes.isEmpty()) {
            throw new BadRequestException("Los artículos no están disponibles: " + articulosNoPresentes);
        }

        ordenDeCompraRespository.save(ordenDeCompra);
        ordenCompraDetalleRepository.saveAll(ordenCompra);
    }
}
