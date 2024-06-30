import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';

const OrdenesDeCompra = () => {
  const [ordenesDeCompra, setOrdenesDeCompra] = useState([]);

  useEffect(() => {
    const fetchOrdenesDeCompra = async () => {
      try {
        const response = await fetch('http://localhost:8080/ordenes-de-compra');
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setOrdenesDeCompra(data.ordenDeCompras); // Asumo que el arreglo de órdenes está en data.ordenDeCompras
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchOrdenesDeCompra();
  }, []); // Ejecutar solo una vez al montar el componente

  return (
    <div className="container mt-4">
      <h2>Órdenes de Compra</h2>
      {ordenesDeCompra.map((orden) => (
        <div key={orden.idOrdenDeCompra} className="card mb-3">
          <div className="card-header">
            Orden de Compra #{orden.idOrdenDeCompra}
          </div>
          <div className="card-body">
            <h5 className="card-title">Detalles de la Orden</h5>
            <ul className="list-group list-group-flush">
              {orden.ordenesCompraDetalle.map((detalle) => (
                <li key={detalle.id} className="list-group-item">
                  <div className="row">
                    <div className="col">
                      <strong>Artículo:</strong> {detalle.articulo.nombre}
                    </div>
                    <div className="col">
                      <strong>Cantidad:</strong> {detalle.cantidad}
                    </div>
                  </div>
                  <div className="row mt-2">
                    <div className="col">
                      <strong>Descripción:</strong> {detalle.articulo.descripcion}
                    </div>
                    <div className="col">
                      <strong>Precio:</strong> {detalle.articulo.precio}
                    </div>
                  </div>
                  <div className="row mt-2">
                    <div className="col">
                      <strong>Proveedor:</strong> {detalle.articulo.proveedor.nombre}
                    </div>
                    <div className="col">
                      <strong>Tipo de Artículo:</strong> {detalle.articulo.tipoArticulo.nombre}
                    </div>
                  </div>
                  <div className="row mt-2">
                    <div className="col">
                      <strong>Fecha de Creación del Artículo:</strong> {detalle.articulo.fechaAlta}
                    </div>
                    <div className="col">
                      <strong>Estado de la Orden:</strong> {orden.estadoOrdenDeCompra.nombreEstadoOrdenDeCompra}
                    </div>
                  </div>
                </li>
              ))}
            </ul>
            <p className="card-text mt-2">
              <strong>Fecha de Creación de la Orden:</strong> {orden.fechaCreacion}
            </p>
            <p className="card-text">
              <strong>Fecha de Actualización de la Orden:</strong> {orden.fechaActualizacion}
            </p>
          </div>
        </div>
      ))}
       <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>
  );
};

export default OrdenesDeCompra;
