import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const CrearOrdenCompra = () => {

  const [idArticulo, setIdArticulo] = useState(null); 
  const [demanda, setDemanda] = useState(null); 
  const navigate = useNavigate();

  const handleCalculateDemand = async () => {
    try {
      if (idArticulo) {
        const response = await axios.post(`http://localhost:8080/validar-stock/${idArticulo}`);
        console.log(response);
        navigate('/ordenes-de-compra');
      } else {
        console.error('Debe seleccionar un artículo para validar el stock.');
      }
    } catch (error) {
      console.error('Error al calcular la demanda:', error);
    }
  };

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
            <tr key={articulo.codArticulo}>
              <td><input type="checkbox" onChange={() => handleArticuloSelection(articulo.codArticulo)} /></td>
              <td>{articulo.codArticulo}</td>
              <td>{articulo.nombre}</td>
              <td>{articulo.inventario.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <button type="button" className="btn btn-primary" onClick={handleCalculateDemand}>Crear orden de compra</button>
      <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>
  );
};

export default CrearOrdenCompra;
