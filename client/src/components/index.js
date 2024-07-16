import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import SearchBar from './SearchBar';
import { toast } from 'react-toastify';
import CrearArticulo from './CrearArticulo';
import ActualizarArticulo from './ActualizarArticulo';
import Card from './Card';
import { AiFillAliwangwang } from "react-icons/ai";
import { BiLastPage } from "react-icons/bi";
import CrearVenta from './CrearVenta';
import OrdenesDeCompra from './OrdenDeCompra';

class Articulos extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      articulos: [],
      search: '',
      filteredArticle: [],
      filter: '',
      create: false,
      showCreate: false,
      update: false,
      showUpdate: false,
      id: 0,
      currentPage: 0,
      showSales: false,
      newSaleArticle: 0,
      newAutoOrden: null,
      showModal: true,
      quantityPerPage: 5,
    };
  }

  filterArticlebyStock = (articulos, filter) => {
    if (filter === 'stock seguridad') {
      const filtered = articulos.filter(
        articulo =>
          articulo.stock + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.stock_seguridad ?? 0)
      );
      return filtered;
    }
    if (filter === 'punto pedido') {
      const filtered = articulos.filter(
        articulo =>
          articulo.stock + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.punto_pedido ?? 0)
      );
      return filtered;
    }
    return articulos;
  };

  searchArticle = async (search, filter) => {
    const { articulos } = this.state;
    const filtered = articulos.filter(articulo =>
      articulo?.nombre?.toLowerCase().includes(search.toLowerCase())
    );
    if (search === '') {
      this.setState({ filteredArticle: this.filterArticlebyStock(articulos, filter) });
    }
    this.setState({ filteredArticle: this.filterArticlebyStock(filtered, filter) });
  };

  fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/articulos');
      this.setState({ articulos: response.data, filteredArticle: response.data });
    } catch (error) {
      console.error('Error fetching data:', error);
      toast.error('Error fetching data');
    }
  };

  bajaArticulo = async (id) => {
    try {
      const response = await axios.delete(`http://localhost:8080/baja/articulo/${id}`);
      toast.success(response.data.message);
      this.fetchData();
    } catch (error) {
      if (error.response.status === 500) {
        toast.error('Error al eliminar el artículo');
      } else {
        toast.error(error.response.data.message);
      }
    }
  };

  toggleCreate = () => {
    this.setState({ showCreate: !this.state.showCreate });
  };

  createArticle = async (nombre, stock, precio, modeloInventario, tasaRotacion) => {
    try {
      const response = await axios.post('/api/articulos/create', {
        nombre,
        stock,
        precio,
        modeloInventario,
        tasaRotacion,
      });
      this.setState({ create: !this.state.create });
      toast.success('Artículo creado correctamente');
      this.toggleCreate();
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  toggleUpdate = () => {
    this.setState({ showUpdate: !this.state.showUpdate });
  };

  updateArticle = async (nombre, stock, precio, modeloInventario, tasaRotacion, id) => {
    try {
      const response = await axios.put('/api/articulos/update', {
        nombre,
        stock,
        precio,
        id,
        modeloInventario,
        tasaRotacion,
      });
      this.setState({ update: !this.state.update });
      toast.success('Artículo actualizado correctamente');
      this.toggleUpdate();
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  newSale = async (payload) => {
    try {
      const response = await axios.post('/api/venta/create', {
        articulo_id: payload.article,
        cantidad: payload.quantity,
        fecha: payload.date,
      });
      toast.success(response.data.message);
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  openNewSale = (id) => {
    this.setState({ newSaleArticle: id, showSales: true });
  };

  totalPages = () => {
    const { filteredArticle, quantityPerPage } = this.state;
    return Math.ceil(filteredArticle.length / quantityPerPage);
  };

  displayedArticles = () => {
    const { currentPage, quantityPerPage, filteredArticle } = this.state;
    return filteredArticle.slice(
      currentPage * quantityPerPage,
      (currentPage + 1) * quantityPerPage
    );
  };

  handlePageChange = (newPage) => {
    this.setState({ currentPage: newPage });
  };

  getPaginationButtons = () => {
    const { currentPage } = this.state;
    const totalPages = this.totalPages();
    const buttons = [];
    let startPage = currentPage;

    if (currentPage + 4 > totalPages) {
      startPage = totalPages - 4;
    }

    startPage = Math.max(startPage, 0);

    for (let i = 0; i < totalPages; i++) { // Solo mostramos 5 botones de paginación
      const pageNumber = startPage + i + 1;
      buttons.push(
        <button
          key={i}
          className={`btn btn-sm mx-1 ${
            currentPage === pageNumber - 1
              ? 'btn-primary text-white'
              : 'btn-secondary text-black hover:bg-gray-400'
          }`}
          onClick={() => this.handlePageChange(pageNumber - 1)}
          disabled={pageNumber > totalPages} // Deshabilitamos el botón si es mayor que el total de páginas
        >
          {pageNumber}
        </button>
      );
    }
    return buttons;
  };

  componentDidMount() {
    this.fetchData();
  }

  componentDidUpdate(prevProps, prevState) {
    const { create, update } = this.state;
    if (create !== prevState.create || update !== prevState.update) {
      this.fetchData();
    }
  }

  showAutoOrden = (open) => {
    if (open) return;

    this.setState({ newAutoOrden: null });
  };

  createAutoOrder = async (articuloId) => {
    try {
      // get articulo
      const responseArticulo = await axios.get(`/api/articulos/${articuloId}`);

      const responseInv = await axios.post('/api/inventario/calcInventario', {
        idArticulo: articuloId,
      });

      if (!responseInv.data?.articulo?.CalculosInventario?.loteOptimo) {
        toast.warn('Datos no disponibles para auto orden');
        return;
      }

      const autoOrder = {
        articulo: {
          id: responseArticulo.data.id,
          stock: responseArticulo.data.stock,
        },
        articuloProveedor: {
          id: responseInv.data.articulo.idProveedor,
          costoPedido: responseInv.data.articulo.costo_pedido,
          plazoEntrega: responseInv.data.articulo.plazo_entrega,
          precioUnitario: responseInv.data.articulo.precio_unidad,
          proveedor: responseInv.data.articulo.nombreProveedor,
        },
        orderQ: Math.round(responseInv.data?.articulo?.CalculosInventario?.loteOptimo),
      };

      // show modal
      this.setState({ newAutoOrden: autoOrder });
      toast.success('Datos configurados al menor CGI');
    } catch (error) {
      console.error('Error creating auto order:', error);
      toast.error('Error al crear la orden automática');
    }
  };

  sendOrder = async (order, orderId) => {
    try {
      await axios.put('/api/ordenes/update', {
        ...order,
        id: orderId,
        estado: 'Enviada',
        fecha: new Date().toISOString().split('T')[0],
      });
      toast.success('Orden de compra enviada exitosamente');
    } catch (error) {
      toast.error('Error al enviar la orden de compra');
    }
  };

  createAutoOrden = async (order, send) => {
    try {
      const orderId = await axios.post('/api/ordenes/create', order);
      toast.success('Orden de compra creada exitosamente');
      send && this.sendOrder(order, orderId.data.lastID);
      this.setState({ newAutoOrden: null });
    } catch (error) {
      toast.error('Error al crear la orden de compra');
    }
  };

  render() {
    const { showCreate, showUpdate, currentPage, showSales, newAutoOrden } = this.state;

    return (
      <div className="container p-4 rounded-lg shadow-sm bg-light">
        <div className="row no-gutters">
          <div className="col-12 col-md-6 d-flex justify-content-center">
            <h1 className="text-center display-4 font-weight-bold mb-4">Artículos</h1>
          </div>
          <div className="col-12 col-md-6 d-flex align-items-center justify-content-between">
            <SearchBar setSearch={this.setSearch} />
            <div className="form-group d-flex flex-grow-1 ml-2">
              <select
                className="form-control rounded-0"
                onChange={(e) => this.setFilter(e.target.value)}
              >
                <option value="">Filtrar por...</option>
                <option value="stock seguridad">Stock de Seguridad</option>
                <option value="punto pedido">Punto de Pedido</option>
              </select>
            </div>
          </div>
        </div>

        <div className="row row-cols-1 row-cols-md-2 g-4 mt-4">
          {this.displayedArticles().map((articulo) => (
            <Card
              key={articulo.idArticulo}
              articulo={articulo}
              deleteArticle={this.bajaArticulo}
              toggleUpdate={this.toggleUpdate}
              setId={this.setId}
              createAutoOrder={this.createAutoOrder} // Assuming createAutoOrder function
              newSale={this.openNewSale} // Assuming openNewSale function
            />
          ))}
        </div>

        {/* Button for creating new article */}
        <div className="fixed-bottom d-flex justify-content-end m-3">
          <button
            className="btn btn-primary rounded-pill shadow-sm"
            onClick={this.toggleCreate}
          >
            <AiFillAliwangwang className="me-2" /> Nuevo Artículo
          </button>
        </div>

        {/* Form for creating a new article */}
        {showCreate && (
          <CrearArticulo toggleCreate={this.toggleCreate} createArticle={this.createArticle} />
        )}

        {/* Form for updating an article */}
        {showUpdate && (
          <ActualizarArticulo toggleUpdate={this.toggleUpdate} updateArticle={this.updateArticle} id={this.state.id} />
        )}

        {/* Pagination */}
        <div className="flex justify-center mt-6">
          <BiLastPage
            className={`w-6 h-6 ${
              currentPage === 0
                ? 'text-gray-300'
                : 'text-black cursor-pointer hover:text-black'
            }`}
            onClick={() => currentPage > 0 && this.handlePageChange(currentPage - 1)}
          />
          {this.getPaginationButtons()}
          <BiLastPage
            className={`w-6 h-6 ${
              currentPage === this.totalPages() - 1
                ? 'text-gray-300'
                : 'text-black cursor-pointer hover:text-black'
            }`}
            onClick={() =>
              currentPage < this.totalPages() - 1 && this.handlePageChange(currentPage + 1)
            }
          />
        </div>

        {/* Modal */}
        <div className={`modal fade ${this.state.showModal ? 'show' : ''}`} style={{ display: this.state.showModal ? 'block' : 'none' }} tabIndex="-1" role="dialog">
          <div className="modal-dialog modal-dialog-centered" role="document">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Ejemplo de Modal</h5>
                <button type="button" className="btn-close" onClick={() => this.setState({ showModal: false })}></button>
              </div>
              <div className="modal-body">
                Contenido del modal...
              </div>
              <div className="modal-footer">
                <button type="button" className="btn btn-secondary" onClick={() => this.setState({ showModal: false })}>Cerrar</button>
                <button type="button" className="btn btn-primary">Guardar cambios</button>
              </div>
            </div>
          </div>
        </div>

        {showSales && (
          <CrearVenta />
        )}
        {newAutoOrden && (
          <OrdenesDeCompra />
        )}
      </div>
    );
  }
}

export default Articulos;
