package com.operativa.gestion.service;

import com.operativa.gestion.dto.DemandaDto;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.OrdenCompraDetalle;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.repository.OrdenCompraDetalleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventarioService {

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ArticuloRepository articuloRepository;
    private final OrdenCompraDetalleRepository ordenDeCompraRespository;
    private final DemandaService demandaService;

    public InventarioService(ArticuloRepository articuloRepository, OrdenCompraDetalleRepository ordenDeCompraRespository,
                             DemandaService demandaService) {
        this.articuloRepository = articuloRepository;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
        this.demandaService = demandaService;
    }

    public List<Articulo> obtenerInventarioPorModelo(String modelo, String tipoArticulo) {
        return articuloRepository.findByTipoArticuloAndModeloInventario(tipoArticulo, modelo);
    }

    public BigDecimal calcularLoteOptimo(Articulo articulo) {
        LocalDate fechaActual = LocalDate.now();
        String fechaInicio = fechaActual.minusMonths(3).format(formatter);
        String fechaFin = fechaActual.format(formatter);

        BigDecimal demanda = demandaService.calcularDemanda(new DemandaDto(fechaInicio, fechaFin,articulo.getCodArticulo()));
        BigDecimal costoAlmacenamiento = articulo.getCostoAlmacenamiento();
        BigDecimal costoPedido = articulo.getProveedor().getCostoPedido();

        BigDecimal dos = BigDecimal.valueOf(2);
        BigDecimal costoPedidoDivididoCostoAlmacenamiento = costoPedido.divide(costoAlmacenamiento);
        BigDecimal raizCuadrada = dos.multiply(demanda).multiply(costoPedidoDivididoCostoAlmacenamiento).sqrt(new MathContext(20));

        return raizCuadrada;
    }

    public BigDecimal calcularPuntoPedido(Articulo articulo) {
        LocalDate fechaActual = LocalDate.now();
        String fechaInicio = fechaActual.minusMonths(3).format(formatter);
        String fechaFin = fechaActual.format(formatter);

        BigDecimal demanda = demandaService.calcularDemanda(new DemandaDto(fechaInicio, fechaFin, articulo.getCodArticulo()));
        BigDecimal demandaDiaria = demanda.divide(BigDecimal.valueOf(30), RoundingMode.HALF_UP);
        Long tiempoDemora = articulo.getProveedor().getTiempoDemora();

        BigDecimal resultado = BigDecimal.valueOf(0);

        resultado = resultado.add(BigDecimal.valueOf(tiempoDemora).multiply(demandaDiaria));

        return resultado;
    }

    public BigDecimal calcularCgi(Articulo articulo) {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaInicio = fechaActual.minusMonths(3).format(formatter);
        String fechaFin = fechaActual.format(formatter);

        BigDecimal demanda = demandaService.calcularDemanda(new DemandaDto(fechaInicio, fechaFin, articulo.getCodArticulo()));

        BigDecimal loteOptimo = calcularLoteOptimo(articulo);

        BigDecimal multiploUno = demanda.divide(loteOptimo, 2, RoundingMode.HALF_UP)
                .multiply(articulo.getProveedor().getCostoPedido());

        BigDecimal multiploDos = loteOptimo.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP)
                .multiply(articulo.getCostoAlmacenamiento());

        BigDecimal multiploTres = articulo.getPrecio().multiply(demanda);

        return multiploUno.add(multiploDos).add(multiploTres);
    }

    public List<Articulo> articulosReponer(String modeloInventario, String tipoArticulo) {
     List<Articulo> articulosReponer = new ArrayList<>();
     for (Articulo articulo : articuloRepository.findByTipoArticuloAndModeloInventario(tipoArticulo, modeloInventario)) {
         List<OrdenCompraDetalle> ordenDeCompra = ordenDeCompraRespository.findByArticuloIdAndStatus(articulo.getCodArticulo());

         if(articulo.getInventario().getStock() <= calcularPuntoPedido(articulo).intValue() && ordenDeCompra.isEmpty()) {
             articulosReponer.add(articulo);
         }
     }
        return articulosReponer;
    }

    public List<Articulo> articulosFaltantes(String modeloInventario, String tipoArticulo) {
        List<Articulo> articulosFaltantes = new ArrayList<>();
        for (Articulo articulo : articuloRepository.findByTipoArticuloAndModeloInventario(tipoArticulo, modeloInventario)) {
            if(articulo.getInventario().getStock() <= articulo.getInventario().getStockSeguridad()) {
                articulosFaltantes.add(articulo);
            }
        }
        return articulosFaltantes;
    }
}
