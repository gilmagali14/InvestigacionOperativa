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
    private final OrdenDeCompraService ordenDeCompraService;
    private final InventarioService inventarioService;
    private final ArticuloProveedorRepository articuloProveedorRepository;
    private final ProveedorRepository proveedorRepository;

    public VentaService(VentaRepository ventaRepository,
                        ArticuloRepository articuloRepository,
                        ArticuloVentaRepository articuloVentaRepository,
                        OrdenDeCompraService ordenDeCompraService,
                        InventarioService inventarioService,
                        ArticuloProveedorRepository articuloProveedorRepository,
                        ProveedorRepository proveedorRepository) {
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
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
        ArticuloProveedor articuloProveedor = articuloProveedorRepository.findByArticuloAndProveedor(articulo, proveedor).get();
        ventaRepository.save(venta);
        articuloRepository.save(articulo);
        articuloProveedor = inventarioService.updateArticuloInventario(articuloProveedor);

        ArticuloVenta articuloVenta = new ArticuloVenta();
        articuloVenta.setCantidadArticulos(cantidad);
        articuloVenta.setAno(ventaDTO.getAño());
        articuloVenta.setMes(ventaDTO.getMes());
        articuloVenta.setDia(ventaDTO.getDia());
        articuloVenta.setArticulo(articulo);
        articuloVenta.setVenta(venta);
        articuloProveedorRepository.save(articuloProveedor);

        if (inventarioService.menorQuePuntoPedido(articulo.getStock(), articuloProveedor.getPuntoPedido()) &&
                articuloProveedor.getModelo().equals("lote-fijo")) {
            ordenDeCompraService.crearOrden(articuloProveedor, articuloProveedor.getLoteOptimo());
        }
        articuloVentaRepository.save(articuloVenta);
        return "Venta creada correctamente";
    }

    public List<VentasDTO> mostrarVentasPorArticulo(Long idArticulo) {
        List<ArticuloVenta> articuloVentas = articuloVentaRepository.findArticuloVentaByArticuloId(idArticulo);
        List<VentasDTO> ventasDTO = new ArrayList<>();
        for (ArticuloVenta articuloVenta : articuloVentas) {
            VentasDTO ventas = new VentasDTO();
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
