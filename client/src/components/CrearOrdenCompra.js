import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const DemandaComponent = () => {

  const [idArticulo, setIdArticulo] = useState(null); 
  const [demandaCalculada, setDemandaCalculada] = useState(null); 
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
      <h2 className="my-4">Validar Stock</h2>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Artículo</th>
            <th scope="col">Código</th>
            <th scope="col">Nombre</th>
          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.codArticulo}>
              <td><input type="checkbox" onChange={() => handleArticuloSelection(articulo.codArticulo)} /></td>
              <td>{articulo.codArticulo}</td>
              <td>{articulo.nombre}</td>
            </tr>
          ))}
        </tbody>
      </table>
      
      <button type="button" className="btn btn-primary" onClick={handleCalculateDemand}>Validar Stock</button>
      <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>
  );
};

export default DemandaComponent;
