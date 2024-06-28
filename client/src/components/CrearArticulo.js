import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const CrearArticulo = () => {
  const navigate = useNavigate();
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [precio, setPrecio] = useState(0); // Estado para el precio
  const [costoAlmacenamiento, setCostoAlmacenamiento] = useState(0); // Estado para el costo de almacenamiento
  const [tipoArticulos, setTipoArticulos] = useState([]);
  const [selectedTipoArticulo, setSelectedTipoArticulo] = useState('');
  const [proveedores, setProveedores] = useState([]);
  const [selectedProveedor, setSelectedProveedor] = useState('');

  useEffect(() => {
    obtenerTipoArticulos();
    obtenerProveedores();
  }, []);

  const obtenerTipoArticulos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/tipo-articulos');
      setTipoArticulos(response.data);
    } catch (error) {
      console.error('Error al obtener tipos de artículos:', error);
    }
  };

  const obtenerProveedores = async () => {
    try {
      const response = await axios.get('http://localhost:8080/proveedores');
      setProveedores(response.data);
    } catch (error) {
      console.error('Error al obtener tipos de proveedores:', error);
    }
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/create/articulo', {
        nombre,
        descripcion,
        nombreTipoArticulo: selectedTipoArticulo, // Usando nombreTipoArticulo en lugar de tipoArticuloId
        nombreProveedor: selectedProveedor, // Usando nombreProveedor en lugar de proveedor
        precio,
        costoAlmacenamiento
      });
      navigate('/articulos');
      console.log(response.data);
    } catch (error) {
      console.error('Error al crear el artículo:', error);
    }
  };

  return (
    <div className="container-fluid d-flex justify-content-center align-items-center vh-100">
      <div className="p-5 m-5 bg-light">
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="nombre" className="form-label">Nombre</label>
            <input
              type="text"
              className="form-control"
              id="nombre"
              value={nombre}
              onChange={(e) => setNombre(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="descripcion" className="form-label">Descripción</label>
            <textarea
              className="form-control"
              id="descripcion"
              value={descripcion}
              onChange={(e) => setDescripcion(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="precio" className="form-label">Precio</label>
            <input
              type="number"
              className="form-control"
              id="precio"
              value={precio}
              onChange={(e) => setPrecio(parseFloat(e.target.value))}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="costoAlmacenamiento" className="form-label">Costo de Almacenamiento</label>
            <input
              type="number"
              className="form-control"
              id="costoAlmacenamiento"
              value={costoAlmacenamiento}
              onChange={(e) => setCostoAlmacenamiento(parseFloat(e.target.value))}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="tipoArticulo" className="form-label">Tipo de Artículo</label>
            <select
              className="form-select"
              id="tipoArticulo"
              value={selectedTipoArticulo}
              onChange={(e) => setSelectedTipoArticulo(e.target.value)}
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
          <div className="mb-3">
            <label htmlFor="proveedor" className="form-label">Proveedor</label>
            <select
              className="form-select"
              id="proveedor"
              value={selectedProveedor}
              onChange={(e) => setSelectedProveedor(e.target.value)}
              required
            >
              <option value="">Seleccionar Proveedor</option>
              {proveedores.map((prov) => (
                <option key={prov.nombre} value={prov.nombre}>
                  {prov.nombre}
                </option>
              ))}
            </select>
          </div>
          <button type="submit" className="btn btn-primary">Crear Artículo</button>
        </form>
      </div>
    </div>
  );
};

export default CrearArticulo;
