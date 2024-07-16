package com.operativa.gestion.service;

import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.HistoricoDemandaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService {

    private final HistoricoDemandaRepository historicoDemandaRepository;

    public InventarioService(HistoricoDemandaRepository historicoDemandaRepository) {
        this.historicoDemandaRepository = historicoDemandaRepository;
    }

    public Boolean menorQuePuntoPedido(int stock, double puntoPedido) {
        return stock <= puntoPedido;
    }
    public ArticuloProveedor updateArticuloInventario(ArticuloProveedor articuloProveedor) {
        int demandaAnual = getDemandaHistorica(articuloProveedor.getArticulo());
        articuloProveedor.setLoteOptimo(calcularLoteOptimo(articuloProveedor.getArticulo(), articuloProveedor.getCostoPedido(),
                articuloProveedor.getModelo(), demandaAnual));
        articuloProveedor.setPuntoPedido(calcularPuntoPedido(articuloProveedor.getCostoPedido(), articuloProveedor.getModelo(),
                articuloProveedor.getTiempoEntrega(), demandaAnual));
        articuloProveedor.setStockSeguridad(calcularStockSeguridad(articuloProveedor.getArticulo(), articuloProveedor.getTiempoEntrega()));
        return articuloProveedor;
    }

    private int getDemandaHistorica(Articulo articulo) {
        List<HistoricoDemanda> list = historicoDemandaRepository.findHistoricoDemandaByArticuloAndAño(articulo, LocalDate.now().getYear());
        int sum = 0;
        for (HistoricoDemanda historicoDemanda : list) {
            sum += historicoDemanda.getCantidad();
        }
        return sum;
    }

    public double calcularLoteOptimo(Articulo articulo, double costoPedido, String modelo, int demandaAnual) {
        double costoAlmacenamiento = articulo.getPrecio() * articulo.getTasaRotacion();
        double costo = costoPedido / costoAlmacenamiento;
        int loteOptimo = 0;
        switch (modelo) {
            case "lote-fijo":
                loteOptimo = (int)Math.ceil(Math.sqrt(2*demandaAnual*costo));
                break;
            case "intervalo-fijo":
                loteOptimo = articulo.getStock();
                break;
        }
        return loteOptimo > 0 ? loteOptimo : 0;
    }

    public Double calcularPuntoPedido(double costoPedido, String modelo, int tiempoEntrega, int demandaAnual) {
        double puntoPedido = demandaAnual * costoPedido;
        double demandaDiaria = demandaAnual / 245.0;
        double desviacionEstandar = demandaDiaria * 0.05;
        double z = 1.64;
        switch (modelo) {
            case "lote-fijo":
                puntoPedido = demandaDiaria * costoPedido;
                break;
            case "intervalo-fijo":
                puntoPedido = demandaDiaria * costoPedido +  z * desviacionEstandar * Math.sqrt(tiempoEntrega);
                break;
        }
        return puntoPedido > 0 ? puntoPedido : 0;
    }

    public Double calcularCgi(Articulo articulo, double costoPedido, String modelo, int demandaAnual) {
        double loteOptimo = calcularLoteOptimo(articulo, costoPedido, modelo, demandaAnual);
        double purchaseCost = articulo.getPrecio() * loteOptimo;
        double costoAlmacenamiento = articulo.getPrecio() * articulo.getTasaRotacion();
        double storageCost =  costoAlmacenamiento* (loteOptimo / 2) ;
        double orderCost = costoPedido * (demandaAnual / loteOptimo);
        double cgi = purchaseCost + storageCost + orderCost;
        return cgi > 0 ? cgi : 0;
    }

    public Double calcularStockSeguridad(Articulo articulo, int tiempoEntrega) {
        int demandaAnual = getDemandaHistorica(articulo);
        double demandaDiaria = demandaAnual / 245.0;
        double desviacionEstandar = demandaDiaria * 0.05;
        double z = 1.64;
        double stockSeguridad = z * desviacionEstandar * Math.sqrt(tiempoEntrega);
        return stockSeguridad > 0 ? stockSeguridad : 0;
    }
}
