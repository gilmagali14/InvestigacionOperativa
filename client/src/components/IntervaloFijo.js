import React, { useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import ArticulosFaltantes from './ArticulosFaltantes';
import ArticulosReponer from './ArticulosReponer';

const LoteFijo = ({ articulos }) => {
    const [mostrarFaltantes, setMostrarFaltantes] = useState(false);
    const [mostrarReponer, setMostrarReponer] = useState(false);
    const [articulosFaltantes, setArticulosFaltantes] = useState([]);
    const [articulosReponer, setArticulosReponer] = useState([]);

    const handleMostrarFaltantes = async (articulo) => {
        try {
            const response = await axios.get(`http://localhost:8080/inventario/intervalo-fijo/${articulo.tipoArticulo.nombre}/articulos-faltantes`);
            setArticulosFaltantes(response.data);
            setMostrarFaltantes(true);
        } catch (error) {
            console.error('Error al obtener los artículos faltantes:', error);
        }
    };

    const handleMostrarReponer = async (articulo) => {
        try {
            const response = await axios.get(`http://localhost:8080/inventario/intervalo-fijo/${articulo.tipoArticulo.nombre}/articulos-reponer`);
            setArticulosReponer(response.data);
            setMostrarReponer(true);
        } catch (error) {
            console.error('Error al obtener los artículos a reponer:', error);
        }
    };

    return (
        <div className="container">
            {articulos.length > 0 && (
                <div className="mt-4" style={{ maxWidth: '800px', margin: '0 auto' }}>
                    <h3>Artículos</h3>
                    <table className="table table-bordered">
                        <thead>
                            <tr>
                                <th>Código</th>
                                <th>Nombre</th>
                                <th>Stock de Seguridad</th>
                            </tr>
                        </thead>
                        <tbody>
                            {articulos.map((articulo) => (
                                <tr key={articulo.codArticulo}>
                                    <td>{articulo.codArticulo}</td>
                                    <td>{articulo.nombre}</td>
                                    <td>{articulo.inventario.stockSeguridad}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            )}
            <div>
            <div className="row">
            <div className="col">
            {!mostrarReponer && (
                <button className="btn btn-primary" onClick={() => handleMostrarReponer(articulos[0])}>
                    Mostrar Artículos a Reponer
                </button>
            )}

            {!mostrarFaltantes && (
                <button className="btn btn-primary" onClick={() => handleMostrarFaltantes(articulos[0])}>
                    Mostrar Artículos Faltantes
                </button>
            )}

            {mostrarReponer && <ArticulosReponer articulos={articulosReponer} />}
            {mostrarFaltantes && <ArticulosFaltantes articulos={articulosFaltantes} />}
            </div>

            </div>

            </div>
        </div>
    );
};

export default LoteFijo;
