import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const CrearOrdenCompra = () => {

  const [idArticulo, setIdArticulo] = useState(null); 
  const [demanda, setDemanda] = useState(null); 

  const [articulos, setArticulos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const url = window.location.href;
        const partesUrl = url.split('/');
        const ultimoValor = partesUrl[partesUrl.length - 1];
        setDemanda(ultimoValor);
        console.log(ultimoValor)
        const response = await fetch('http://localhost:8080/articulos');
        const data = await response.json();
        setArticulos(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    
    fetchData();
  }, []);

  const handleArticuloSelection = (id) => {
    setIdArticulo(id);
  };

  return (
    <div className="container">
      <h2 className="my-4">Crear Orden de compra</h2>
      <div><strong>Demanda Calculada:</strong> {demanda}</div>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Artículo</th>
            <th scope="col">Código</th>
            <th scope="col">Nombre</th>
            <th scope="col">Stock disponible</th>

          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.idArticulo}>
              <td><input type="checkbox" onChange={() => handleArticuloSelection(articulo.idArticulo)} /></td>
              <td>{articulo.idArticulo}</td>
              <td>{articulo.nombre}</td>
              <td>{articulo.inventario.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <button type="button" className="btn btn-primary">Crear orden de compra</button>
      <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>
  );
};

export default CrearOrdenCompra;
