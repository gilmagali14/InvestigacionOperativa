import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { toast } from 'react-toastify';

const ActualizarArticulo = ({ toggleUpdate, id }) => {
  const [tipoArticulos, setTipoArticulos] = useState([]);
  const [proveedores, setProveedores] = useState([]);
  const [articulo, setArticulo] = useState({
    nombre: '',
    precio: 0,
    descripcion: '',
    nombreTipoArticulo: '',
    stock: 0,
    tasaRotacion: '',
    proveedor: ''
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        await obtenerTipoArticulos();
        await obtenerProveedores();
        await fetchArticulo();
      } catch (error) {
        console.error('Error en la carga de datos:', error);
      }
    };

    fetchData();
  }, [id]); 

  const obtenerProveedores = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/articulo/proveedores/${id}`);
      if (response.data.length === 0) {
        toast.warn('Debe asociar un proveedor');
      } else {
        setProveedores(response.data);
      }
    } catch (error) {
      console.error('Error al obtener proveedores:', error);
    }
  };

  const obtenerTipoArticulos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/tipo-articulos');
      setTipoArticulos(response.data);
    } catch (error) {
      console.error('Error al obtener tipos de artículos:', error);
    }
  };

  const fetchArticulo = async () => {
    try {
      const response = await fetch(`http://localhost:8080/articulo/${id}`);
      const data = await response.json();
      setArticulo({
        nombre: data.nombre,
        precio: data.precio,
        descripcion: data.descripcion,
        nombreTipoArticulo: data.tipoArticulo.nombre,
        stock: data.stock,
        tasaRotacion: data.tasaRotacion,
        proveedor: data.proveedor || ''
      });
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const body = {
        id: id,
        nombre: articulo.nombre,
        descripcion: articulo.descripcion,
        nombreTipoArticulo: articulo.nombreTipoArticulo,
        precio: articulo.precio,
        stock: articulo.stock,
        tasaRotacion: articulo.tasaRotacion,
        proveedor: articulo.proveedor
      };
        
      await axios.put('http://localhost:8080/actualizar/articulo', body);
      toast.success('Artículo actualizado exitosamente');
      toggleUpdate(false);
    } catch (error) {
      console.error('Error al actualizar el artículo:', error);
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
              <h5 className="modal-title">Actualizar Artículo</h5>
              <button type="button" className="btn-close" onClick={() => toggleUpdate(false)}></button>
            </div>
            <div className="modal-body">
              {id !== null && (
                <form onSubmit={handleSubmit}>
                  <div className="row mb-3">
                    <div className="col-md-6">
                      <label htmlFor="nombre" className="form-label">Nombre</label>
                      <input
                        type="text"
                        className="form-control"
                        id="nombre"
                        name="nombre"
                        value={articulo.nombre}
                        onChange={handleChange}
                        required
                      />
                    </div>
                    <div className="col-md-6">
                      <label htmlFor="precio" className="form-label">Precio</label>
                         <input
                        type="number"
                        min="0"
                        className="form-control"
                        id="precio"
                        name="precio"
                        value={articulo.precio}
                        onChange={handleChange}
                        required
                      />
                    </div>
                  </div>
                  <div className="row mb-3">
                    <div className="col-md-6">
                      <label htmlFor="descripcion" className="form-label">Descripción</label>
                      <textarea
                        className="form-control"
                        id="descripcion"
                        name="descripcion"
                        value={articulo.descripcion}
                        onChange={handleChange}
                        required
                      />
                    </div>
                    <div className="col-md-6">
                      <label htmlFor="tipoArticulo" className="form-label">Tipo de Artículo</label>
                      <select
                        className="form-select"
                        id="tipoArticulo"
                        name="nombreTipoArticulo"
                        value={articulo.nombreTipoArticulo}
                        onChange={handleChange}
                        required
                      >
                        <option value="">Seleccionar Tipo</option>
                        {tipoArticulos.map((tipo) => (
                          <option key={tipo.nombre} value={tipo.nombre}>
                            {tipo.nombre}
                          </option>
                        ))}
                      </select>
                    </div>
                  </div>
                  <div className="row mb-3">
                    <div className="col-md-6">
                      <label htmlFor="stock" className="form-label">Stock</label>
                         <input
                        type="number"
                        min="0"
                        className="form-control"
                        id="stock"
                        name="stock"
                        value={articulo.stock}
                        onChange={handleChange}
                        required
                      />
                    </div>
                    <div className="col-md-6">
                      <label htmlFor="tasaRotacion" className="form-label">Tasa de Rotacion</label>
                         <input
                        type="number"
                        min="0"
                        className="form-control"
                        id="tasaRotacion"
                        name="tasaRotacion"
                        value={articulo.tasaRotacion}
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
                          {proveedores.map((proveedor) => (
                            <option key={proveedor.nombre} value={proveedor.nombre}>
                              {proveedor.nombre}
                            </option>
                          ))}
                        </select>
                      </div>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col-md-12">
                      <button type="submit" className="btn btn-primary">Actualizar Artículo</button>
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

export default ActualizarArticulo;
