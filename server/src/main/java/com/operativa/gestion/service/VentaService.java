package com.operativa.gestion.service;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VentaService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final VentaRepository ventaRepository;
    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;
    private final DemandaRepository demandaRepository;
    private final InventarioRepository inventarioRepository;

    public VentaService(OrdenDeCompraService ordenDeCompraService,
                        VentaRepository ventaRepository,
                        ArticuloRepository articuloRepository,
                        ArticuloVentaRepository articuloVentaRepository,
                        DemandaRepository demandaRepository,
                        InventarioRepository inventarioRepository) {
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
        this.demandaRepository = demandaRepository;
        this.inventarioRepository = inventarioRepository;
    }

    public List<ArticuloOrdenCompraDTO> crearVenta(VentaDTO venta) throws BadRequestException {
        List<ArticuloVentaDTO> articulos = venta.getArticulos();
        BigDecimal montoTotal = BigDecimal.valueOf(0);
        LocalDate primerDiaDelMes = LocalDate.now().withDayOfMonth(1);
        List<ArticuloOrdenCompraDTO> articuloOrdenCompraDTOS = new ArrayList<>();

        List<ArticuloVenta> articuloVentas = new ArrayList<>();
        for (ArticuloVentaDTO x : articulos) {
            Long id = x.getIdArticuloVenta();
            Optional<Articulo> articulo = articuloRepository.findById(id);
            if (articulo.isPresent()) {
                Articulo art = articulo.get();
                montoTotal = montoTotal.add(art.getPrecio().multiply(BigDecimal.valueOf(x.getCantidadArticulo())));
                Inventario inventario = art.getInventario();
                Long stock = inventario.getStock();
                Long resta = stock - x.getCantidadArticulo();

                if (resta < inventario.getStockSeguridad()) {
                    throw new BadRequestException("El stock no puede ser negativo");
                }
                if (resta <= inventario.getPuntoPedido()) {
                    Long cantidadFinal = (inventario.getPuntoPedido() - resta)/inventario.getLoteOptimo();

                    ArticuloOrdenCompraDTO articuloOrdenCompraDTO = new ArticuloOrdenCompraDTO(art.getCodArticulo(),
                                                                                               cantidadFinal.intValue());

                    articuloOrdenCompraDTOS.add(articuloOrdenCompraDTO);
                }
                inventario.setStock(resta);
                inventarioRepository.save(inventario);
                articuloVentas.add(new ArticuloVenta(art, x.getCantidadArticulo()));
                List<Demanda> demanda = demandaRepository.findDemandasByFechas(art.getCodArticulo(), primerDiaDelMes.format(formatter));
                for (Demanda demanda1 : demanda) {
                    long suma = demanda1.getCantidad() + x.getCantidadArticulo();
                    demanda1.setCantidad((int) suma);
                    demandaRepository.save(demanda1);
                }

            } else {
                throw new RuntimeException("Artículo no encontrado con ID: " + id);
            }
        }

        Venta nuevaVenta = new Venta(montoTotal);
        ventaRepository.save(nuevaVenta);
        articuloVentas.stream().forEach(x -> {
            x.setVenta(nuevaVenta);
            x.setFechaVenta(venta.getFechaVenta());
            articuloVentaRepository.save(x);
        });

        return articuloOrdenCompraDTOS;
    }

    public List<VentasDTO> mostrarVentasPorArticulo(Long idArticulo) {
        List<ArticuloVenta> articuloVentas = articuloVentaRepository.findArticuloVentaByArticuloId(idArticulo);
        List<VentasDTO> ventasDTO = new ArrayList<>();
        for (ArticuloVenta articuloVenta : articuloVentas) {
            VentasDTO ventas = new VentasDTO();
            ventas.setFechaVenta(articuloVenta.getFechaVenta());
            ventas.setVenta(articuloVenta.getVenta());
            ventas.setArticulo(articuloVenta.getArticulo());
            ventas.setCantidadArticulos(articuloVenta.getCantidadArticulos());
            ventasDTO.add(ventas);

        }
        return ventasDTO;
    }

    public List<ArticuloVenta> mostrarVentas() {
        return articuloVentaRepository.findAll();
    }
}
