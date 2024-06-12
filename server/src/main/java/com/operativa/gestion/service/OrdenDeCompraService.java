package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloOrdenCompraDTO;
import com.operativa.gestion.dto.OrdenDeCompraDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.model.OrdenDeCompra;
import com.operativa.gestion.model.repository.OrdenCompraDetalleRepository;
import com.operativa.gestion.model.repository.OrdenDeCompraRespository;
import com.operativa.gestion.model.repository.TipoArticuloRespository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenDeCompraService {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final TipoArticuloRespository tipoArticuloRespository;
    private final OrdenDeCompraRespository ordenDeCompraRespository;
    private final ArticuloService articuloService;

    public OrdenDeCompraService(OrdenCompraDetalleRepository ordenCompraDetalleRepository, TipoArticuloRespository tipoArticuloRespository,
                                OrdenDeCompraRespository ordenDeCompraRespository,
                                ArticuloService articuloService) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.articuloService = articuloService;
    }

    public OrdenDeCompra crearOrdenDeCompra(OrdenDeCompraDTO ordenDeCompraDto) throws BadRequestException {
        List<ArticuloOrdenCompraDTO> articulosIds = ordenDeCompraDto.getArticulos();
        OrdenDeCompra ordenDeCompra = ordenDeCompraDto.getOrdenDeCompra();
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
        return ordenDeCompra;
    }
}
