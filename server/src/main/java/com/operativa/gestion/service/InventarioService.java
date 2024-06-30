package com.operativa.gestion.service;

import com.operativa.gestion.dto.LoteOptimoDTO;
import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.ArticuloVenta;
import com.operativa.gestion.model.Inventario;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.repository.ArticuloVentaRepository;
import com.operativa.gestion.model.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloVentaRepository articuloVentaRepository;
    private final InventarioRepository inventarioRepository;

    public InventarioService(ArticuloRepository articuloRepository, ArticuloVentaRepository articuloVentaRepository, InventarioRepository inventarioRepository) {
        this.articuloRepository = articuloRepository;
        this.articuloVentaRepository = articuloVentaRepository;
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> obtenerInventarioPorModelo(String modelo) {
        return inventarioRepository.findAllByModelo(modelo);
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
}
