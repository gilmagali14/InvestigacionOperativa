package com.operativa.gestion.service;

import com.operativa.gestion.model.Articulo;
import com.operativa.gestion.model.Demanda;
import com.operativa.gestion.model.repository.ArticuloRepository;
import com.operativa.gestion.model.repository.DemandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class DemandaService {
    private DemandaRepository demandaRepository;
    private ArticuloRepository articuloRepository;

    public Demanda crearDemanda() {
        // LA DEMANDA se actualiza todos los meses
        //List<Articulo> articulos = articuloRepository.(fecha);

       // demandaRepository.save(demanda);
        return null;
    }
}
