import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const CrearVenta = ({ setShowModal, id }) => {
  const [proveedores, setProveedores] = useState([])
  const [articulo, setArticulo] = useState({
    cantidad: 0,
    proveedor: '',
    fecha: ''
  });

  useEffect(() => {
    obtenerProveedores();
  }, []);

  const obtenerProveedores = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/articulo/proveedores/${id}`);
      setProveedores(response.data);
    } catch (error) {
      console.error('Error al obtener proveedores:', error);
    }
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const body = {
        idArticulo: id,
        fecha: articulo.fecha,
        proveedor: articulo.proveedor,
        cantidadArticulo: articulo.cantidad
      };
      console.log(body)
      await axios.post('http://localhost:8080/crear/venta', body);
      toast.success('Venta creada exitosamente');
      setShowModal(false);
    } catch (error) {
      toast.error(error.response.data.message);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setArticulo({ ...articulo, [name]: value });
  };

  return (
    <div className="container mt-4">
      <div className="modal fade show" tabIndex="-1" role="dialog" style={{ display: 'block' }}>
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Crear Venta</h5>
              <button type="button" className="btn-close" onClick={() => setShowModal(false)}></button>
            </div>
            <div className="modal-body">
              <form onSubmit={handleSubmit}>
                <div className="mb-3">
                  <label htmlFor="fecha" className="form-label">
                    Fecha de Venta
                  </label>
                  <input
                    type="date"
                    className="form-control"
                    id="fecha"
                    onChange={handleChange}
                    required
                  />
                </div>
                <div key={id} className="border p-3 mb-3">
                  <div className="mb-3">
                    <label htmlFor="cantidad" className="form-label">
                      Cantidad
                    </label>
                    <input
                      type="number"
                      className="form-control"
                      id="cantidad"
                      name="cantidad"
                      onChange={handleChange}
                      />
                  </div>
                </div>
                <div className="row mb-3">
                    <div className="col-md-6">
                      <label htmlFor="proveedor" className="form-label">Proveedor</label>
                      <select
                        className="form-select"
                        id="proveedor"
                        name="proveedor"
                        value={articulo.proveedor}
                        onChange={handleChange}
                        required
                      >
                       <option value="">Seleccionar Proveedor</option>
                        {proveedores.map((tipo) => (
                            <option key={tipo.nombre} value={tipo.nombre}>
                              {tipo.nombre}
                            </option>
                          )) }
                      </select>
                    </div>
                  </div>
                <button type="submit" className="btn btn-success">
                  Crear Venta
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrearVenta;
