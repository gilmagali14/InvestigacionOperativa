import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const ArticulosComponent = () => {
  const [articulos, setArticulos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('http://localhost:8080/articulos');
        setArticulos(response.data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData();
  }, []);

  const bajaArticulo = async (idArticulo) => {
    try {
      await axios.delete(`http://localhost:8080/baja/articulo/${idArticulo}`);
      const updatedArticulos = articulos.map(articulo =>
      articulo.codArticulo === idArticulo ? { ...articulo, fechaBaja: new Date() } : articulo);
      setArticulos(updatedArticulos);
    } catch (error) {
      console.error('Error al dar de baja artículo:', error);
    }
  };

  const formatDate = (dateString) => {
    if (!dateString) return '';

    const date = new Date(dateString);
    const year = date.getFullYear();
    let month = (date.getMonth() + 1).toString().padStart(2, '0'); 
    let day = date.getDate().toString().padStart(2, '0'); 
    return `${year}-${month}-${day}`;
  };

  return (
    <div className="container">
      <h2 className="my-4">Artículos</h2>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Código</th>
            <th scope="col">Nombre</th>
            <th scope="col">Descripción</th>
            <th scope="col">Precio</th>
            <th scope="col">Fecha Baja</th>
            <th scope="col">Costo de Almacenamiento</th>
            <th scope="col">Tipo de articulo</th>
            <th scope="col">Proveedor</th>
            <th scope="col">Stock</th>
            <th scope="col">Stock de Seguridad</th>
            <th scope="col">Modelo de inventario</th>
          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.codArticulo}>
              <td>{articulo.codArticulo}</td>
              <td>{articulo.nombre}</td>
              <td>{articulo.descripcion}</td>
              <td>{articulo.precio}</td>
              <td>{formatDate(articulo.fechaBaja)}</td> {}
              <td>{articulo.costoAlmacenamiento}</td>
              <td>{articulo.tipoArticulo.nombre}</td>
              <td>{articulo.proveedor.nombre}</td>
              <td>{articulo.inventario.stock}</td>
              <td>{articulo.inventario.stockSeguridad}</td>
              <td>{articulo.inventario.modelo}</td>
              <td>
                <button
                  className="btn btn-danger"
                  onClick={() => bajaArticulo(articulo.codArticulo)}
                  disabled={articulo.fechaBaja !== null}
                >
                  Dar de baja
                </button>
              </td>
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
