import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

const CrearArticulo = ({ toggleCreate }) => {
  const [tipoArticulos, setTipoArticulos] = useState([]);
  const [articulo, setArticulo] = useState({
    nombre: '',
    precio: 0,
    descripcion: '',
    nombreTipoArticulo: '',
    stock: 0,
    tasaRotacion: 0
  });

  useEffect(() => {
    obtenerTipoArticulos();
  }, []);

  const obtenerTipoArticulos = async () => {
    try {
      const response = await axios.get('http://localhost:8080/tipo-articulos');
      setTipoArticulos(response.data);
    } catch (error) {
      console.error('Error al obtener tipos de artículos:', error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/crear/articulo', articulo);
      toggleCreate(false); 
      toast.success('Articulo crado correctamente')
    } catch (error) {
      console.error('Error al crear el artículo:', error);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setArticulo({ ...articulo, [name]: value });
  };

  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
      <div className="modal-dialog" role="document">
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title" id="modalTitle">Crear Artículo</h5>
            <button type="button" className="btn-close" onClick={() => toggleCreate(false)} aria-label="Close"></button>
          </div>
          <div className="modal-body">
            <form onSubmit={handleSubmit}>
              <div className='row'>
              <div className="col-md-6">
                <label htmlFor="nombre" className="form-label">Nombre</label>
                <input
                  type="text"
                  className="form-control"
                  id="nombre"
                  name="nombre"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="precio" className="form-label">Precio</label>
                <input
                  type="number"
                  className="form-control"
                  id="precio"
                  name="precio"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="descripcion" className="form-label">Descripción</label>
                <textarea
                  className="form-control"
                  id="descripcion"
                  name="descripcion"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="tasaRotacion" className="form-label">Tasa de Rotación</label>
                <input
                  type="number"
                  className="form-control"
                  id="tasaRotacion"
                  name="tasaRotacion"
                  onChange={handleChange}
                  required
                />
              </div>
              <div className="col-md-6">
                <label htmlFor="nombreTipoArticulo" className="form-label">Tipo de Artículo</label>
                <select
                  className="form-select"
                  id="nombreTipoArticulo"
                  name="nombreTipoArticulo"
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
              <div className="col-md-6">
                <label htmlFor="stock" className="form-label">Stock</label>
                <input
                  type="number"
                  className="form-control"
                  id="stock"
                  name="stock"
                  onChange={handleChange}
                  required
                />
              </div>
              </div>
              <div className='d-flex justify-content-center'>
              <button type="submit" className="btn btn-primary my-3">Crear Artículo</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrearArticulo;
