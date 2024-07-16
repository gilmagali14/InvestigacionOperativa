import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';

const OrdenesDeCompra = () => {
    const [ordenes, setOrdenes] = useState([]);
    const [estadoSeleccionado, setEstadoSeleccionado] = useState('');
    const estadosDisponibles = ['PENDIENTE', 'ACEPTADA', 'RECHAZADA']; 

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/ordenes');
                setOrdenes(response.data);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleChangeEstado = async (idOrden) => {
        try {

            await axios.put(`http://localhost:8080/orden/${idOrden}/${estadoSeleccionado}`);
            const updatedOrdenes = ordenes.map(orden => {
                if (orden.id === idOrden) {
                    return {
                        ...orden,
                        ordenDeCompra: {
                            ...orden.ordenDeCompra,
                            estadoOrdenDeCompra: {
                                ...orden.ordenDeCompra.estadoOrdenDeCompra,
                                nombreEstadoOrdenDeCompra: estadoSeleccionado
                            }
                        }
                    };
                }
                return orden;
            });
            toast.success("Orden de compra actualizada correctamente")
            setOrdenes(updatedOrdenes);
        } catch (error) {
            console.error('Error updating estado:', error);
        }
    };

    return (
        <div className="container mt-4">
            <h2>Ordenes de Compra</h2>
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Fecha de Creación</th>
                        <th scope="col">Estado</th>
                        <th scope="col">Artículo</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col">Cambiar Estado</th>
                    </tr>
                </thead>
                <tbody>
                    {ordenes.map((orden) => (
                        <tr key={orden.id}>
                            <th scope="row">{orden.id}</th>
                            <td>{orden.ordenDeCompra.fechaCreacion}</td>
                            <td>{orden.ordenDeCompra.estadoOrdenDeCompra.nombreEstadoOrdenDeCompra}</td>
                            <td>{orden.articulo.nombre}</td>
                            <td>{orden.cantidad}</td>
                            <td>
                                <select
                                    className="form-select"
                                    value={estadoSeleccionado}
                                    onChange={(e) => setEstadoSeleccionado(e.target.value)}
                                >
                                    <option value="">Seleccionar estado</option>
                                    {estadosDisponibles.map((estado) => (
                                        <option key={estado} value={estado}>
                                            {estado}
                                        </option>
                                    ))}
                                </select>
                            </td>
                            <td>
                                <button
                                    className="btn btn-primary"
                                    onClick={() => handleChangeEstado(orden.id)}
                                    disabled={!estadoSeleccionado}
                                >
                                    Actualizar
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default OrdenesDeCompra;
