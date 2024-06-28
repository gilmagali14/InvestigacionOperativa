import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const Inventario = () => {
    const [modeloGestion, setModeloGestion] = useState('');
    const [articulos, setArticulos] = useState([]);
    const [tipoArticulo, setTipoArticulo] = useState('');
    const [articulosFiltrados, setArticulosFiltrados] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        if (tipoArticulo) {
            // Obtener los artículos correspondientes al tipo de artículo seleccionado
            const obtenerArticulos = async () => {
                try {
                    const response = await axios.get(`http://localhost:8080/articulos?tipo=${tipoArticulo}`);
                    setArticulosFiltrados(response.data);
                } catch (error) {
                    console.error('Error al obtener los artículos:', error);
                }
            };
            obtenerArticulos();
        } else {
            setArticulosFiltrados([]);
        }
    }, [tipoArticulo]);

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/articulos', { modeloGestion });
            navigate('/inventario'); //
        } catch (error) {
            console.error('Error al enviar datos:', error);
        }
    };

    return (
        <div className="container">
            <form onSubmit={handleFormSubmit} className="w-50 mx-auto d-flex flex-column justify-content-center align-items-center vh-100">
                <div className="mb-3 w-100">
                    <label htmlFor="tipoArticulo" className="form-label">
                        Seleccionar tipo de artículo
                    </label>
                    <select
                        className="form-select"
                        id="tipoArticulo"
                        value={tipoArticulo}
                        onChange={(e) => setTipoArticulo(e.target.value)}
                        required
                    >
                        <option value="">Seleccionar tipo</option>
                        <option value="Tipo1">Limpieza</option>
                        <option value="Tipo2">Tecnología</option>
                        <option value="Tipo3">Indumentaria</option>
                    </select>
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
                        <option value="Modelo de Lote Fijo">Modelo de Lote Fijo</option>
                        <option value="Modelo Intervalo Fijo">Modelo Intervalo Fijo</option>
                    </select>
                </div>
                <button type="submit" className="btn btn-primary">
                    Enviar
                </button>
            </form>

            <div className="w-50 mx-auto">
                <h2>Lista de Artículos</h2>
                <ul className="list-group">
                    {articulosFiltrados.map((articulo) => (
                        <li key={articulo.id} className="list-group-item">
                            {articulo.nombre} - {articulo.tipoArticulo}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Inventario;




