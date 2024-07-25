import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import CrearOrdenCompra from './CrearOrdenCompra';
import { Link } from 'react-router-dom';
import { FaUser } from "react-icons/fa";

const OrdenesDeCompra = () => {
    const [ordenes, setOrdenes] = useState([]);
    const [estadoSeleccionado, setEstadoSeleccionado] = useState('');
    const [showCreate, setShowCreate] = useState(false);
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

    const handleChangeEstado = async (idOrden, proveedor) => {
        try {
            await axios.put(`http://localhost:8080/orden/${idOrden}/${estadoSeleccionado}/${proveedor}`);
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
            toast.success("Orden de compra actualizada correctamente");
            setOrdenes(updatedOrdenes);
        } catch (error) {
            console.error('Error updating estado:', error);
        }
    };

    const toggleCreate = () => setShowCreate(prev => !prev);

    return (
        <div>
        <header className="bg-primary text-white p-3">
          <div className="container d-flex justify-content-between align-items-center">
          <FaUser />
            <h4 className="h3 mb-0">Ordenes de Compra</h4>
            <nav>
              <ul className="nav">
                <li className="nav-item">
                  <Link to="/" className="nav-link text-white">Inicio</Link>
                </li>
              </ul>
            </nav>
          </div>
        </header>
        <div className="container mt-5">
            <table className="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Fecha de Creación</th>
                        <th scope="col">Estado</th>
                        <th scope="col">Artículo</th>
                        <th scope="col">Cantidad</th>
                        <th scope="col">Proveedor</th>
                        <th scope="col">Cambiar Estado</th>
                    </tr>
                </thead>
                <tbody>
                    {ordenes.map((orden) => (
                        <tr key={orden.id}>
                            <th scope="row">{orden.id}</th>
                            <td>{orden.ordenDeCompra.fechaCreacion}</td>
                            <td>{orden.ordenDeCompra.estadoOrdenDeCompra.nombreEstadoOrdenDeCompra}</td>
                            <td>{orden.articuloProveedor.articulo.nombre}</td>
                            <td>{orden.cantidad}</td>
                            <td>{orden.articuloProveedor.proveedor.nombre}</td>
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
                                    onClick={() => handleChangeEstado(orden.id, orden.articuloProveedor.proveedor.nombre)}
                                    disabled={!estadoSeleccionado}
                                >
                                    Actualizar
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="fixed-bottom d-flex justify-content-end m-3">
                <button className="btn btn-primary rounded-pill shadow-sm" onClick={toggleCreate}>
                    Crear orden de compra
                </button>
                {showCreate && <CrearOrdenCompra toggleCreate={toggleCreate} />}
            </div>
        </div>
        </div>
    );
};

export default OrdenesDeCompra;
