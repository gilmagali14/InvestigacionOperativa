package com.operativa.gestion.service;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ArticuloVenta;
import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.repository.ArticuloVentaRepository;
import com.operativa.gestion.model.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service

public class DemandaService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;
    private final ProveedorRepository proveedorRepository;

    public DemandaService(ArticuloRepository articuloRepository, ArticuloVentaRepository articuloVentaRepository, ProveedorRepository proveedorRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public BigDecimal calcularDemanda(DemandaDto demandaDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<ArticuloVenta> articulos = articuloVentaRepository.findArticuloVentaByArticuloIdAndFechaVentaBetween(
                demandaDto.getIdArticulo(),
                LocalDateTime.parse(demandaDto.getFechaDesde(), formatter),
                LocalDateTime.parse(demandaDto.getFechaHasta(), formatter)
        );

        BigDecimal demanda = BigDecimal.ZERO;
        int sumaPeso = 0;
        int pesoInicial = articulos.size();

        for (int i = 0; i < articulos.size(); i++) {
            ArticuloVenta articuloVenta = articulos.get(i);
            int pesoValor = pesoInicial - i;
            sumaPeso += pesoValor;
            BigDecimal cantidadArticulos = BigDecimal.valueOf(articuloVenta.getCantidadArticulos());
            BigDecimal pesoBigDecimal = BigDecimal.valueOf(pesoValor);
            demanda = demanda.add(cantidadArticulos.multiply(pesoBigDecimal));
        }

        if (sumaPeso == 0) {
            return BigDecimal.ZERO;
        }

        return demanda.divide(BigDecimal.valueOf(sumaPeso), 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal calcularLoteOptimo(LoteOptimoDTO loteFijoDto) {
        LocalDateTime fechaInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay(); // Primer día del mes actual a las 00:00:00
        LocalDateTime fechaFin = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1); // Último día del mes actual a las 23:59:59

        List<ArticuloVenta> articulos = articuloVentaRepository.findByArticuloIdAndFechaVentaBetween(
                loteFijoDto.getIdArticulo(), fechaInicio, fechaFin);

        BigDecimal demanda = BigDecimal.ZERO;
        for (ArticuloVenta articuloVenta : articulos) {
            demanda = demanda.add(BigDecimal.valueOf(articuloVenta.getCantidadArticulos()));
        }

        Optional<Articulo> articuloOptional = articuloRepository.findById(loteFijoDto.getIdArticulo());
        if (articuloOptional.isPresent()) {
            Articulo articulo = articuloOptional.get();
            BigDecimal costoAlmacenamiento = articulo.getCostoAlmacenamiento();
            BigDecimal costoPedido = articulo.getProveedor().getCostoPedido();

            BigDecimal dos = BigDecimal.valueOf(2);
            BigDecimal costoPedidoDivididoCostoAlmacenamiento = costoPedido.divide(costoAlmacenamiento, 20, BigDecimal.ROUND_HALF_UP); // Ajustar la escala según sea necesario
            BigDecimal raizCuadrada = dos.multiply(demanda).multiply(costoPedidoDivididoCostoAlmacenamiento).sqrt(new MathContext(20)); // Aquí se define la precisión

            return raizCuadrada;
        } else {
            throw new RuntimeException("Artículo no encontrado con ID: " + loteFijoDto.getIdArticulo());
        }
    }

    public BigDecimal calcularPuntoPedido(LoteOptimoDTO loteFijoDto) {
        LocalDateTime fechaInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay(); // Primer día del mes actual a las 00:00:00
        LocalDateTime fechaFin = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1); // Último día del mes actual a las 23:59:59

        List<ArticuloVenta> articulos = articuloVentaRepository.findByArticuloIdAndFechaVentaBetween(
                loteFijoDto.getIdArticulo(), fechaInicio, fechaFin);

        BigDecimal demanda = BigDecimal.ZERO;
        for (ArticuloVenta articuloVenta : articulos) {
            demanda = demanda.add(BigDecimal.valueOf(articuloVenta.getCantidadArticulos()));
        }
        Optional<Articulo> articuloOptional = articuloRepository.findById(loteFijoDto.getIdArticulo());
        if (articuloOptional.isPresent()) {
            Articulo articulo = articuloOptional.get();
            demanda.multiply(BigDecimal.valueOf(articulo.getProveedor().getTiempoDemora()));
        }
        return demanda;
    }

    public BigDecimal calcularCgi(LoteOptimoDTO loteOptimoDTO) {
        LocalDateTime fechaInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay(); // Primer día del mes actual a las 00:00:00
        LocalDateTime fechaFin = LocalDate.now().plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1); // Último día del mes actual a las 23:59:59

        List<ArticuloVenta> articulos = articuloVentaRepository.findByArticuloIdAndFechaVentaBetween(
                loteOptimoDTO.getIdArticulo(), fechaInicio, fechaFin);

        BigDecimal demanda = BigDecimal.ZERO;
        for (ArticuloVenta articuloVenta : articulos) {
            demanda = demanda.add(BigDecimal.valueOf(articuloVenta.getCantidadArticulos()));
        }
        Optional<Articulo> articuloOptional = articuloRepository.findById(loteOptimoDTO.getIdArticulo());

        List<ArticuloVenta> artVenya = articuloVentaRepository.findByArticuloId(loteOptimoDTO.getIdArticulo());

        Articulo articulo = articuloOptional.get();
        BigDecimal pDivd = new BigDecimal(0);
        pDivd.add(demanda.multiply(articulo.getPrecio()));

        articuloOptional.get().getCostoAlmacenamiento().multiply(calcularLoteOptimo(loteOptimoDTO).divide(BigDecimal.valueOf(2)));
      //  articuloOptional.get().getProveedor().getCostoPedido().multiply(demanda.divide());
        return demanda;
    }
}

