package com.operativa.gestion.service;

import com.operativa.gestion.model.Proveedor;
import com.operativa.gestion.model.TipoArticulo;
import com.operativa.gestion.model.repository.ProveedorRepository;
import com.operativa.gestion.model.repository.TipoArticuloRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SharedService {

    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;

    public SharedService(TipoArticuloRespository tipoArticuloRespository, ProveedorRepository proveedorRepository) {
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
