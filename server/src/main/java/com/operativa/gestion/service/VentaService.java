package com.operativa.gestion.service;

import com.operativa.gestion.dto.*;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class VentaService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final VentaRepository ventaRepository;
    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;
    private final HistoricoDemandaRepository historicoDemandaRepository;
    private final OrdenDeCompraService ordenDeCompraService;
    private final InventarioService inventarioService;
    private final ArticuloProveedorRepository articuloProveedorRepository;
    private final ProveedorRepository proveedorRepository;

    public VentaService(VentaRepository ventaRepository,
                        ArticuloRepository articuloRepository,
                        ArticuloVentaRepository articuloVentaRepository,
                        HistoricoDemandaRepository historicoDemandaRepository,
                        OrdenDeCompraService ordenDeCompraService,
                        InventarioService inventarioService,
                        ArticuloProveedorRepository articuloProveedorRepository,
                        ProveedorRepository proveedorRepository) {
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
        this.historicoDemandaRepository = historicoDemandaRepository;
        this.ordenDeCompraService = ordenDeCompraService;
        this.inventarioService = inventarioService;
        this.articuloProveedorRepository = articuloProveedorRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public String crearVenta(VentaDTO ventaDTO) throws BadRequestException {
        int cantidad =  ventaDTO.getCantidadArticulo();
        Optional<Articulo> art = articuloRepository.findById(ventaDTO.getIdArticulo());
        Articulo articulo;
        if (art.isEmpty()) {
            throw new BadRequestException("Artículo no encontrado");
        }
        articulo = art.get();

        if (articulo.getStock() < cantidad) {
            throw new BadRequestException("El stock no puede ser negativo");
        }
        articulo.setStock(articulo.getStock() - cantidad);
        Venta venta = new Venta(cantidad * articulo.getPrecio());
        Proveedor proveedor = proveedorRepository.findByNombre(ventaDTO.getProveedor()).get();
        saveDemandaHistorica(articulo, cantidad);
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloAndProveedor(articulo, proveedor).get();
        ventaRepository.save(venta);
        articuloRepository.save(articulo);
        articuloProveedor = inventarioService.updateArticuloInventario(articuloProveedor);

        if (inventarioService.menorQuePuntoPedido(articulo.getStock(), articuloProveedor.getPuntoPedido()) &&
                articuloProveedor.getModelo().equals("lote-fijo")) {
            ordenDeCompraService.crearOrden(articulo, articuloProveedor.getLoteOptimo());
        }

        articuloProveedorRepository.save(articuloProveedor);
        return "Venta creada correctamente";
    }

    private void saveDemandaHistorica(Articulo articulo, int cantidad) {
        LocalDate currentDate = LocalDate.now();
        Integer year = currentDate.getYear();
        Integer month = currentDate.getMonthValue();
        Optional<HistoricoDemanda> historicoDemanda = historicoDemandaRepository.findByArticuloAndAñoAndMes(articulo, year, month);
        HistoricoDemanda historico;

        if (historicoDemanda.isPresent()) {
            historico = historicoDemanda.get();
            historico.setCantidad(historico.getCantidad() + cantidad);
        } else {
            historico = new HistoricoDemanda();
            historico.setCantidad(cantidad);
            historico.setArticulo(articulo);
            historico.setMes(month);
            historico.setAño(year);
        }
        historicoDemandaRepository.save(historico);
    }

 /*   public List<ArticuloOrdenCompraDTO> crearVenta(VentaDTO venta) throws BadRequestException {
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
                //todo
                //montoTotal = montoTotal.add(art.getPrecio().multiply(BigDecimal.valueOf(x.getCantidadArticulo())));
                Inventario inventario = art.getInventario();
                Long stock = inventario.getStock();
                Long resta = stock - x.getCantidadArticulo();

                if (resta < inventario.getStockSeguridad()) {
                    throw new BadRequestException("El stock no puede ser negativo");
                }
                if (resta <= inventario.getPuntoPedido()) {
                    Long cantidadFinal = (long) ((inventario.getPuntoPedido() - resta)/inventario.getLoteOptimo());

                    ArticuloOrdenCompraDTO articuloOrdenCompraDTO = new ArticuloOrdenCompraDTO(art.getIdArticulo(),
                                                                                               cantidadFinal.intValue());

                    articuloOrdenCompraDTOS.add(articuloOrdenCompraDTO);
                }
                inventario.setStock(resta);
                inventarioRepository.save(inventario);
                articuloVentas.add(new ArticuloVenta(art, x.()));
                List<Demanda> demanda = demandaRepository.findDemandasByFechas(art.getIdArticulo(), primerDiaDelMes.format(formatter));
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
            x.setFechaVenta(venta.getFecha());
            articuloVentaRepository.save(x);
        });

        return articuloOrdenCompraDTOS;
    }*/

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
