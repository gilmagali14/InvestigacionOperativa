import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const VentasComponent = () => {
  const [ventas, setVentas] = useState([]);
  const [idArticulo, setIdArticulo] = useState(null); 
  const [demanda, setDemanda] = useState(null); 
  
  useEffect(() => {
    const fetchVentas = async () => {
      try {
        const url = window.location.href; 
        const parts = url.split('/ventas/'); 
        const idArticuloFromUrl = parts[1].split('/')[0]; 
        const demanda = parts[1].split('/')[1]; 
        
        setDemanda(demanda);
        setIdArticulo(idArticuloFromUrl); 
        
        const response = await axios.get(`http://localhost:8080/ventas/${idArticuloFromUrl}`);
        setVentas(response.data); 
      } catch (error) {
        console.error('Error fetching ventas:', error);
      }
    };
  
    fetchVentas();
  }, []); 
  
  return (
    <div className="ventas-container">
      <h3>Ventas del Artículo {idArticulo}</h3>
      <div><strong>Demanda Calculada:</strong> {demanda}</div>
      <div className="ventas-list">
        {ventas.map((venta, index) => (
          <div key={index} className="card mb-3">
            <div className="card-body">
              <h5 className="card-title">Código de Venta: {venta.venta.codVenta}</h5>
              <p className="card-text">Monto Total: {venta.venta.montoTotal}</p>
              <p className="card-text">Fecha de Venta: {new Date(venta.fechaVenta).toLocaleString()}</p>
              <p className="card-text">Cantidad de Artículos: {venta.cantidadArticulos}</p>
            </div>
          </div>
        ))}
      </div>
      <div className="mt-3">
                <Link to="/" className="btn btn-primary">Inicio</Link>
            </div>
    </div>
  );
};

export default VentasComponent;
