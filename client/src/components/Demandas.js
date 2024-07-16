import React, { useState, useEffect } from 'react';
import axios from 'axios';

const DemandasComponent = () => {
    const [demandas, setDemandas] = useState([]);
    const [errorCalculado, setErrorCalculado] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/demandas');
                setDemandas(response.data);

                // Después de obtener las demandas, obtener el error calculado
                const errorResponse = await axios.get('http://localhost:8080/calcular-error');
                setErrorCalculado(errorResponse.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };
        
        fetchData();
    }, []);

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Lista de Demandas</h2>
            <table className="table">
                <thead className="thead-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Mes de Inicio</th>
                        <th scope="col">Mes de Fin</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col">Pronóstico</th>
                        <th scope="col">ID Artículo</th>
                    </tr>
                </thead>
                <tbody>
                    {demandas.map(demanda => (
                        <tr key={demanda.idDemanda}>
                            <td>{demanda.idDemanda}</td>
                            <td>{demanda.mesPeriodoInicio}</td>
                            <td>{demanda.mesPeriodoFin}</td>
                            <td>{demanda.cantidad}</td>
                            <td>{demanda.pronostico}</td>
                            <td>{demanda.idArticulo}</td>
                        </tr>
                    ))}
                </tbody>
            </table>

            {/* Tabla para mostrar el error calculado */}
            <h2 className="mt-4 mb-4">Error Calculado</h2>
            <table className="table">
                <thead className="thead-dark">
                    <tr>
                        <th scope="col">ID Demanda</th>
                        <th scope="col">MAE</th>
                        <th scope="col">MAPE</th>
                        <th scope="col">RMSQ</th>
                    </tr>
                </thead>
                <tbody>
                    {errorCalculado.map((error, index) => (
                        <tr key={index}>
                            <td>{index + 1}</td>
                            <td>{error.MAE}</td>
                            <td>{error.MAPE}</td>
                            <td>{error.RMSQ}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default DemandasComponent;
