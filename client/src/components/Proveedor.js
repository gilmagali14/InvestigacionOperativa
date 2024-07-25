import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';

const AddArticuloProveedor = ({ toggleProveedor, id }) => {
  const [proveedores, setProveedores] = useState([]);
  const [articulo, setArticulo] = useState({
    tiempoEntrega: '',
    costoPedido: 0,
    proveedor: '',
    modelo: ''
  });

  useEffect(() => {
    obtenerProveedores();
  }, []);

  const obtenerProveedores = async () => {
    try {
      const response = await axios.get('http://localhost:8080/proveedores');
      setProveedores(response.data);
    } catch (error) {
      console.error('Error al obtener proveedores:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const body = {
        tiempoEntrega: articulo.tiempoEntrega,
        costoPedido: articulo.costoPedido,
        articulo: id,
        proveedor: articulo.proveedor,
        modelo: articulo.modelo,
      };
      console.log(body);

      const response = await axios.post('http://localhost:8080/articulo/proveedor', body);
      console.log(response.data);
     toast.success('Proveedor asignado exitosamente');
      
      toggleProveedor(false);
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
        <div className="modal-dialog modal-lg" role="document">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Asignar proveedor</h5>
              <button type="button" className="btn-close" onClick={() => toggleProveedor(false)}></button>
            </div>
            <div className="modal-body">
              {id !== null && (
                <form onSubmit={handleSubmit}>
                  <div className="row mb-3">
                  </div>
                  <div className="col-md-6">
                      <label htmlFor="tiempoEntrega" className="form-label">Tiempo de entrega</label>
                         <input
                        type="number"
                        min="0"
                        className="form-control"
                        id="tiempoEntrega"
                        name="tiempoEntrega"
                        onChange={handleChange}
                        required
                      />
                    </div>
                    <div className="mb-3">
                    <label htmlFor="modeloGestion" className="form-label">Seleccionar modelo de gestión de inventario</label>
                    <select
                      className="form-select"
                      id="modeloGestion"
                      name="modelo"
                      onChange={handleChange}
                      required
                    >
                      <option value="">Seleccionar modelo</option>
                      <option value="lote-fijo">Modelo de Lote Fijo</option>
                      <option value="intervalo-fijo">Modelo Intervalo Fijo</option>
                    </select>
                  </div>
                    <div className="col-md-6">
                      <label htmlFor="costoPedido" className="form-label">Costo de pedido</label>
                         <input
                        type="number"
                        min="0"
                        className="form-control"
                        id="costoPedido"
                        name="costoPedido"
               
                        onChange={handleChange}
                        required
                      />
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
                        ))}
                      </select>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-12">
                      <button type="submit" className="btn btn-primary">Asignar proveedor</button>
                    </div>
                  </div>

                </form>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default AddArticuloProveedor;
