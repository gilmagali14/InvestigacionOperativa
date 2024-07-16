package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloVentaDTO;
import com.operativa.gestion.dto.VentaDTO;
import com.operativa.gestion.dto.VentasDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SharedService {

    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;

    public SharedService(TipoArticuloRespository tipoArticuloRespository,
                         ProveedorRepository proveedorRepository) {
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.proveedorRepository = proveedorRepository;
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
}


