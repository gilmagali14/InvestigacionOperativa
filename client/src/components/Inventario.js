import React, { useState, useEffect} from 'react';
import axios from 'axios';
import LoteFijo from '../components/LoteFijo';
import IntervaloFijo from './IntervaloFijo';
import { Link } from 'react-router-dom';

const Inventario = () => {
    const [modeloGestion, setModeloGestion] = useState('');
    const [tipoArticulos, setTipoArticulos] = useState([]);
    const [selectedTipoArticulo, setSelectedTipoArticulo] = useState('');
    const [articulos, setArticulos] = useState(null);

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
            const response = await axios.get(`http://localhost:8080/inventario/${modeloGestion}/${selectedTipoArticulo}`);
            setArticulos(response.data);
        } catch (error) {
            console.error('Error al obtener los artículos:', error);
        }
    };

    const renderComponente = () => {
        if (!articulos) return null; // No renderizar hasta que haya datos

        if (modeloGestion === "lote-fijo") {
            return <LoteFijo articulos={articulos} />;
        } else if (modeloGestion === "intervalo-fijo") {
            return <IntervaloFijo articulos={articulos} />;
        } else {
            return null;
        }
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit} style={{ maxWidth: '600px', margin: '0 auto' }}>
                <div className='m-3'>
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
                </div>
                <button type="submit" className="btn btn-primary">
                    Calcular
                </button>
            </form>

            <div className="mt-5">
                {renderComponente()}
            </div>
            <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
        </div>
    );
};

export default Inventario;
