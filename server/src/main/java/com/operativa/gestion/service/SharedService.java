package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloVentaDTO;
import com.operativa.gestion.dto.VentaDTO;
import com.operativa.gestion.dto.VentasDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SharedService {

    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;
    private final VentaRepository ventaRepository;
    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;

    public SharedService(TipoArticuloRespository tipoArticuloRespository, ProveedorRepository proveedorRepository, VentaRepository ventaRepository, ArticuloRepository articuloRepository, ArticuloVentaRepository articuloVentaRepository) {
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.proveedorRepository = proveedorRepository;
        this.ventaRepository = ventaRepository;
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
    }

    public TipoArticulo crearTipoArticulo(TipoArticulo tipoArticulo) {
        return tipoArticuloRespository.save(tipoArticulo);
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<TipoArticulo> obtenerTipoArticulos() {
        return tipoArticuloRespository.findAll();
    }

    public List<Proveedor> obtenerProveedores() {
        return proveedorRepository.findAll();
    }

    public Venta crearVenta(VentaDTO venta) {
        List<ArticuloVentaDTO> articulos = venta.getArticulos();
        BigDecimal montoTotal = BigDecimal.valueOf(0);

        List<ArticuloVenta> articuloVentas = new ArrayList<>();
        for (ArticuloVentaDTO x : articulos) {
            Long id = x.getIdArticuloVenta();
            Optional<Articulo> articulo = articuloRepository.findById(id);
            if (articulo.isPresent()) {
                BigDecimal montoArticulo = articulo.get().getPrecio().multiply(BigDecimal.valueOf(x.getCantidadArticulo()));
                montoTotal = montoTotal.add(montoArticulo);
                articuloVentas.add(new ArticuloVenta(articulo.get(), x.getCantidadArticulo()));
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

        return nuevaVenta;
    }

    public List<VentasDTO> mostrarVentasPorArticulo(Long idArticulo) {
        List<ArticuloVenta> articuloVentas = articuloVentaRepository.findArticuloVentaByArticuloId(idArticulo);
        List<VentasDTO> ventasDTO = new ArrayList<>();
        for (ArticuloVenta articuloVenta : articuloVentas) {
            VentasDTO ventas = new VentasDTO();
            ventas.setFechaVenta(articuloVenta.getFechaVenta());
            ventas.setVenta(articuloVenta.getVenta());
            ventas.setCantidadArticulos(articuloVenta.getCantidadArticulos());
            ventasDTO.add(ventas);
       }
        return ventasDTO;
    }
}
