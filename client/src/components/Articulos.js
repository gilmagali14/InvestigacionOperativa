import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

const ArticulosComponent = () => {
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

  return (
    <div className="container">
    <h2 className="my-4">Artículos</h2>
    <table className="table">
      <thead>
        <tr>
          <th scope="col">Código de Artículo</th>
          <th scope="col">Nombre</th>
          <th scope="col">Descripción</th>
          <th scope="col">Fecha baja</th>
        </tr>
      </thead>
      <tbody>
        {articulos.map(articulo => (
          <tr key={articulo.id}>
            <td>{articulo.id}</td>
            <td>{articulo.nombre}</td>
            <td>{articulo.descripcion}</td>
            <td>{articulo.updatedAt}</td>
            
          </tr>
        ))}
      </tbody>
    </table>
    <div className="mt-3">
                <Link to="/" className="btn btn-primary">Inicio</Link>
            </div>
  </div>
);
};

export default ArticulosComponent;
