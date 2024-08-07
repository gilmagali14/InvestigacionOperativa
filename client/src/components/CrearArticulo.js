import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const CrearArticulo = () => {
  const navigate = useNavigate();
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [precio, setPrecio] = useState(0);
  const [costoAlmacenamiento, setCostoAlmacenamiento] = useState(0);
  const [tipoArticulos, setTipoArticulos] = useState([]);
  const [selectedTipoArticulo, setSelectedTipoArticulo] = useState('');
  const [proveedores, setProveedores] = useState([]);
  const [selectedProveedor, setSelectedProveedor] = useState('');
  const [stock, setStock] = useState(0); 
  const [stockSeguridad, setStockSeguridad] = useState(0); 
  const [modeloGestion, setModeloGestion] = useState('');  

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
      const response = await axios.post('http://localhost:8080/crear/articulo', {
        nombre,
        descripcion,
        nombreTipoArticulo: selectedTipoArticulo,
        nombreProveedor: selectedProveedor,
        precio,
        costoAlmacenamiento,
        stock,
        stockSeguridad,
        modelo: modeloGestion
      });
      navigate('/articulos');
      console.log(response.data);
    } catch (error) {
      console.error('Error al crear el artículo:', error);
    }
  };

  return (
    <div>
    <div className="container-fluid d-flex justify-content-center align-items-center vh-100">
      <div className="p-5 m-5 bg-light">
        <div className="container-fluid d-flex justify-content-center align-items-center m-3">
      <h4 className='mb-3'>Crear Articulo</h4>
</div>
        <form onSubmit={handleSubmit}>
          <div className="row mb-3">
            <div className="col-md-6">
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
            <div className="col-md-6">
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
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
              <label htmlFor="descripcion" className="form-label">Descripción</label>
              <textarea
                className="form-control"
                id="descripcion"
                value={descripcion}
                onChange={(e) => setDescripcion(e.target.value)}
                required
              />
            </div>
            <div className="col-md-6">
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
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
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
            <div className="col-md-6">
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
          </div>
          <div className="row mb-3">
            <div className="col-md-6">
              <label htmlFor="stock" className="form-label">Stock</label>
              <input
                type="number"
                className="form-control"
                id="stock"
                value={stock}
                onChange={(e) => setStock(parseInt(e.target.value))}
                required
              />
            </div>
          </div>
          <div className="col-md-6">
            <label htmlFor="stockSeguridad" className="form-label">Stock de Seguridad</label>
            <input
              type="number"
              className="form-control"
              id="stockSeguridad"
              value={stockSeguridad}
              onChange={(e) => setStockSeguridad(parseInt(e.target.value))}
              required
            />
          </div>
          <div className="mb-3 w-100">
            <label htmlFor="modeloGestion" className="form-label">
              Seleccionar modelo de gestión de inventario
                </label>
                <select
                    className="form-select"
                    id="modeloGestion"
                    value={modeloGestion}
                    onChange={(e) => setModeloGestion(e.target.value)}
                    required
                >
                    <option value="">Seleccionar modelo</option>
                    <option value="lote-fijo">Modelo de Lote Fijo</option>
                    <option value="intervalo-fijo">Modelo Intervalo Fijo</option>
                </select>
          </div>
          <div className="row">
            <div className="col-md-12">
              <button type="submit" className="btn btn-primary">Crear Artículo</button>
            </div>
          </div>
        </form>
      </div>
      </div>

    <div>
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>

  );
};

export default CrearArticulo;
