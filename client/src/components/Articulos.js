import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const ArticulosComponent = () => {
  const [articulos, setArticulos] = useState([]);

  const bajaArticulo = async (idArticulo) => {
    try { 
      await axios.get(`http://localhost:8080/delete/articulo/${idArticulo}`);
    } catch (error) {
      console.error('Error al dar de baja articulo:', error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/articulos');
        const data = await response.json();
        console.log(data)
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
          <th scope="col">Artículo</th>
          <th scope="col">Codigo</th>
          <th scope="col">Nombre</th>
          <th scope="col">Descripción</th>
          <th scope="col">Fecha baja</th>
        </tr>
      </thead>
      <tbody>
      {articulos.map(articulo => (
        <tr key={articulo.id}>
          <td><input type="checkbox" /></td> {/* Agregar checkbox */}
          <td>{articulo.codArticulo}</td>
          <td>{articulo.nombre}</td>
          <td>{articulo.descripcion}</td>
          <td>{articulo.fecha ? articulo.fecha : <button onClick={() => bajaArticulo('dd')}> </button>}</td>
    </tr>  
        ))}
      </tbody>
    </table>
    <div className="mt-3">
  <button className="btn btn-primary" onClick={<Link to="/" className="btn btn-primary">Inicio</Link>}>Crear Orden de Compra</button>
</div>
    <div className="mt-3">
                <Link to="/" className="btn btn-primary">Inicio</Link>
            </div>
  </div>
);
};

export default ArticulosComponent;
