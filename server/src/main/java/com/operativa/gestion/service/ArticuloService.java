package com.operativa.gestion.service;

import com.operativa.gestion.dto.ArticuloDTO;
import com.operativa.gestion.dto.ArticuloProveedorDTO;
import com.operativa.gestion.dto.ArticulosProveedoresDTO;
import com.operativa.gestion.model.*;
import com.operativa.gestion.model.repository.*;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    private final OrdenCompraDetalleRepository ordenCompraDetalleRepository;
    private final ArticuloRepository articuloRepository;
    private final TipoArticuloRespository tipoArticuloRespository;
    private final ProveedorRepository proveedorRepository;
    private final ArticuloProveedorRepository articuloProveedorRepository;
    private final InventarioService inventarioService;
    private final OrdenDeCompraRespository ordenDeCompraRespository;

    public ArticuloService(OrdenCompraDetalleRepository ordenCompraDetalleRepository, ArticuloRepository articuloRepository,
                           TipoArticuloRespository tipoArticuloRespository,
                           ProveedorRepository proveedorRepository, InventarioService inventarioService,
                           ArticuloProveedorRepository articuloProveedorRepository, OrdenDeCompraRespository ordenDeCompraRespository) {
        this.ordenCompraDetalleRepository = ordenCompraDetalleRepository;
        this.articuloRepository = articuloRepository;
        this.tipoArticuloRespository = tipoArticuloRespository;
        this.proveedorRepository = proveedorRepository;
        this.articuloProveedorRepository = articuloProveedorRepository;
        this.inventarioService = inventarioService;
        this.ordenDeCompraRespository = ordenDeCompraRespository;
    }

    public void crearArticulo(ArticuloDTO articuloDTO) throws BadRequestException {
        Optional<TipoArticulo> tipoArticulo = tipoArticuloRespository.findByNombre(articuloDTO.getNombreTipoArticulo());

        if (tipoArticulo.isEmpty()) {
            throw new BadRequestException("El tipo de articulo no existe");
        }

        Articulo articulo = new Articulo(articuloDTO.getNombre(), articuloDTO.getDescripcion(), articuloDTO.getPrecio(),
                                         articuloDTO.getTasaRotacion(), tipoArticulo.get(), articuloDTO.getStock());

        articuloRepository.save(articulo);
    }

    public void borrarArticulo(long idArticulo) throws BadRequestException {
        List<OrdenCompraDetalle> ordenesDeCompras = ordenCompraDetalleRepository.findByArticuloId(idArticulo);
        if(!ordenesDeCompras.isEmpty()) {
            for (OrdenCompraDetalle orden : ordenesDeCompras) {
                String estado = orden.getOrdenDeCompra().getEstadoOrdenDeCompra().getNombreEstadoOrdenDeCompra();
                if (!estado.equals("RESOLVED")) {
                    throw new BadRequestException("El artículo posee una orden de compra en curso: " + idArticulo);
                }
            }
        }
        Optional<Articulo> articulo = articuloRepository.findById(idArticulo);
        Articulo art = articulo.get();
        art.setFechaBaja(LocalDateTime.now());
        articuloRepository.save(art);
    }

    public void actualizarArticulo(ArticuloDTO articuloDTO) throws BadRequestException {
        Optional<Articulo> articulo = articuloRepository.findById(articuloDTO.getId());
        if (articulo.isEmpty()) {
            throw new BadRequestException("El articulo no existe");
        }
        Optional<TipoArticulo> tipoArticulo = tipoArticuloRespository.findByNombre(articuloDTO.getNombreTipoArticulo());

        if (tipoArticulo.isEmpty()) {
            throw new BadRequestException("El tipo de articulo o proveedor no existe");
        }
        Articulo art = articulo.get();
        art.setNombre(articuloDTO.getNombre());
        art.setDescripcion(articuloDTO.getDescripcion());
        art.setPrecio(articuloDTO.getPrecio());
        art.setTipoArticulo(tipoArticulo.get());
        art.setTasaRotacion(articuloDTO.getTasaRotacion());
        art.setStock(articuloDTO.getStock());
        articuloRepository.save(art);
    }

    public List<Articulo> obtenerArticulos() {
        return articuloRepository.findAll();
    }

    public Articulo obtenerArticuloPorId(Long idArticulo) {
        return articuloRepository.findById(idArticulo).get();
    }

    public ArticuloProveedor crearArticuloProveedor(ArticuloProveedorDTO articuloProveedorDTO) throws BadRequestException {
        Articulo articulo = articuloRepository.findById(articuloProveedorDTO.getArticulo()).get();
        Proveedor proveedor = proveedorRepository.findByNombre(articuloProveedorDTO.getProveedor()).get();

        Optional<ArticuloProveedor> art = articuloProveedorRepository.findByArticuloAndProveedor(articulo, proveedor);

        if (art.isPresent()) {
            throw new BadRequestException("El articulo ya esta asociado a ese proveedor");
        }
        double costoPedido = articuloProveedorDTO.getCostoPedido();

        ArticuloProveedor articuloProveedor = new ArticuloProveedor();
        articuloProveedor.setTiempoEntrega(articuloProveedorDTO.getTiempoEntrega());
        articuloProveedor.setCostoPedido(articuloProveedorDTO.getCostoPedido());
        articuloProveedor.setArticulo(articulo);
        articuloProveedor.setProveedor(proveedor);
        articuloProveedor.setModelo(articuloProveedorDTO.getModelo());
       articuloProveedor = inventarioService.updateArticuloInventario(articuloProveedor);
        articuloProveedorRepository.save(articuloProveedor);
        articuloRepository.save(articulo);

        return articuloProveedor;
    }

    public List<ArticulosProveedoresDTO> articuloProveedores() {
        List<Articulo> articulos = articuloRepository.findAll();
        List<ArticuloProveedorDTO> list = new ArrayList<>();
        List<ArticulosProveedoresDTO> listaFinal = new ArrayList<>();
        for (Articulo articulo : articulos) {
            ArticulosProveedoresDTO dtoFinal = new ArticulosProveedoresDTO();
            List<ArticuloProveedor> articuloProveedors = articuloProveedorRepository.findAllByArticulo(articulo);
            dtoFinal.setNombreArticulo(articulo.getNombre());
            dtoFinal.setDescripcion(articulo.getDescripcion());
            dtoFinal.setStock(articulo.getStock());
            dtoFinal.setPrecio(articulo.getPrecio());
            dtoFinal.setTipoArticulo(articulo.getTipoArticulo().getNombre());
            dtoFinal.setIdArticulo(articulo.getIdArticulo());
            if (!articuloProveedors.isEmpty()) {
                for (ArticuloProveedor articuloProveedor : articuloProveedors) {
                    list.add(new ArticuloProveedorDTO(articuloProveedor.getTiempoEntrega(),
                                                      articuloProveedor.getCostoPedido(),
                                                      articuloProveedor.getProveedor().getNombre(),
                                                      articuloProveedor.getModelo(),
                                                      articuloProveedor.getStockSeguridad(),
                                                      articuloProveedor.getLoteOptimo(),
                            articuloProveedor.getPuntoPedido()));
                }
                dtoFinal.setArticuloProveedor(list);
            }
            listaFinal.add(dtoFinal);
        }
        return listaFinal;
    }

    public List<ArticulosProveedoresDTO> articulosReponer() {
        List<ArticulosProveedoresDTO> articulosReponer = new ArrayList<>();
        List<ArticulosProveedoresDTO> articuloProveedores = articuloProveedores();
        for (ArticulosProveedoresDTO artDto : articuloProveedores) {
            List<OrdenCompraDetalle> ordenDeCompra = ordenCompraDetalleRepository.findByArticuloIdAndStatus(artDto.getIdArticulo());
            if (!ordenDeCompra.isEmpty()) {
                for (ArticuloProveedorDTO articuloProveedorDTO : artDto.getArticuloProveedor()) {
                    if (artDto.getStock() <= articuloProveedorDTO.getPuntoPedido()) {
                        articulosReponer.add(artDto);
                    }
                }
            }
        }
        return articulosReponer;
    }

    public List<Proveedor> proveedoresPorArticulo(Long id) {
        Articulo articulo = articuloRepository.findById(id).get();
        List<ArticuloProveedor> articuloProveedors = articuloProveedorRepository.findAllByArticulo(articulo);
        List<Proveedor> proveedores = new ArrayList<>();
        for (ArticuloProveedor articuloProveedor : articuloProveedors) {
            proveedores.add(articuloProveedor.getProveedor());
        }
        return proveedores;
    }
}



