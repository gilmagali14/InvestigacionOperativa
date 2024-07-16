import React from 'react';
import { Link } from 'react-router-dom';

const ArticulosFaltantes = ({articulos}) => {
  return (
    <div className="container">
      <h2 className="my-4">Artículos</h2>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Código</th>
            <th scope="col">Nombre</th>
            <th scope="col">Precio</th>
            <th scope="col">Stock</th>
            <th scope="col">Stock Seguridad</th>

          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.codArticulo}>
              <td>{articulo.codArticulo}</td>
              <td>{articulo.nombre}</td>
              <td>{articulo.precio}</td>
              <td>{articulo.inventario.stock}</td>
              <td>{articulo.inventario.stockSeguridad}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ArticulosFaltantes;
