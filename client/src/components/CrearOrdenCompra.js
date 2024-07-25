import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

const CrearOrdenCompra = ({ toggleCreate }) => {
  const [articulos, setArticulos] = useState([]);
  const [selectedArticulo, setSelectedArticulo] = useState(null);
  const [show, setShow] = useState(true);
  const [proveedores, setProveedores] = useState([]);
  const [orden, setCrearOrden] = useState({
    idArticulo: '',
    proveedor: '',
    cantidad: ''
  });

  // Fetch articulos on component mount
  useEffect(() => {
    const fetchArticulos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/articulos/proveedores');
        console.log('Datos recibidos de la API:', response.data);
        setArticulos(response.data);
      } catch (error) {
        console.error('Error fetching articulos:', error);
      }
    };
    fetchArticulos();
  }, []);

  // Fetch providers by article id
  const obtenerProveedores = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8080/articulo/proveedores/${id}`);
      console.log('Proveedores obtenidos:', response.data);
      setProveedores(response.data);
    } catch (error) {
      console.error('Error obtaining providers:', error);
    }
  };

  // Handle form submission
  const handleSubmit = async () => {
    if (!orden.idArticulo || !orden.proveedor || !orden.cantidad) {
      alert('Todos los campos deben ser llenados.');
      return;
    }
    try {
      console.log(orden)
      await axios.post('http://localhost:8080/crear/ordenes', orden);
      alert('Orden de compra creada con éxito');
      toggleCreate();
    } catch (error) {
      toast.error(error.response.data.message)
      console.error('Error creating order:', error);
    }
  };

  // Close the modal
  const handleClose = () => {
    setShow(false);
    toggleCreate();
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCrearOrden({ ...orden, [name]: value });
  };

  useEffect(() => {
    console.log('Artículos:', articulos);
    console.log('Artículo seleccionado:', selectedArticulo);
    if (selectedArticulo && selectedArticulo.idArticulo) {
      obtenerProveedores(selectedArticulo.idArticulo);
    }
  }, [selectedArticulo]);

  const handleArticuloSelection = (e) => {
    const id = e.target.value;
    console.log('ID del artículo seleccionado:', id); 

    const articulo = articulos.find(a => a.idArticulo.toString() === id);
    console.log('Artículo encontrado:', articulo);

    setSelectedArticulo(articulo || null);

    setCrearOrden(prevOrden => ({
      ...prevOrden,
      idArticulo: id,
      proveedor: '',
      cantidad: ''
    }));
  };

  return (
    <div className={`modal fade ${show ? 'show' : ''}`} style={{ display: show ? 'block' : 'none' }} tabIndex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="modalLabel">Crear Orden de Compra</h5>
            <button type="button" className="close" onClick={handleClose} aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div className="modal-body">
            <div className="form-group">
              <label htmlFor="articulo-select">Seleccionar Artículo</label>
              <select
                id="articulo-select"
                className="form-control"
                value={orden.idArticulo}
                onChange={handleArticuloSelection}
              >
                <option value="">Seleccione un artículo</option>
                {articulos.map((articulo) => (
                  <option key={articulo.idArticulo} value={articulo.idArticulo}>
                    {articulo.nombreArticulo}
                  </option>
                ))}
              </select>
            </div>
            {selectedArticulo && (
              <div className="mt-4">
                <h4>Detalles del Artículo</h4>
                <span>
                  Lote óptimo sugerido: {selectedArticulo.articuloProveedor[0].loteOptimo || '0'}
                </span>
                <div className="form-group">
                  <label htmlFor="cantidad">Cantidad</label>
                  <input
                    type="number"
                    min="0"
                    id="cantidad"
                    name="cantidad"
                    value={orden.cantidad}
                    onChange={handleChange}
                    className="form-control"
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="proveedor">Proveedor</label>
                  <select
                    id="proveedor"
                    name="proveedor"
                    value={orden.proveedor}
                    onChange={handleChange}
                    className="form-control"
                  >
                    <option value="">Seleccione un proveedor</option>
                    {proveedores.map((proveedor) => (
                      <option key={proveedor.id} value={proveedor.nombre}>
                        {proveedor.nombre}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
            )}
          </div>
          <div className="modal-footer">
            <button onClick={handleSubmit} className="btn btn-primary">Crear Orden</button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrearOrdenCompra;
