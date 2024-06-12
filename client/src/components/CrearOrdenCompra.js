import React, { useState, useEffect} from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const CrearOrdenCompra = () => {
const navigate = useNavigate();
const [nombre, setNombre] = useState('');
const [descripcion, setDescripcion] = useState('');
const [tipoArticulos, setTipoArticulos] = useState([]);
const [selectedTipoArticulo, setSelectedTipoArticulo] = useState('');

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
        const response = await axios.post('http://localhost:8080/create/articulo', {
            nombre,
            descripcion,
            tipoArticuloId: selectedTipoArticulo,
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
      <form  onSubmit={handleSubmit}>
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
              <option key={tipo.id} value={tipo.id}>
                {tipo.nombre}
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

export default CrearOrdenCompra;
