import React from 'react';

const ArticulosReponer = ({articulos}) => {
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
            <th scope="col">Tipo de articulo</th>
            <th scope="col">Proveedor</th>
            <th scope="col">Stock</th>
          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.idArticulo}>
              <td>{articulo.idArticulo}</td>
              <td>{articulo.nombre}</td>
              <td>{articulo.descripcion}</td>
              <td>{articulo.precio}</td>
              <td>{articulo.tipoArticulo.nombre}</td>
              <td>{articulo.proveedor.nombre}</td>
              <td>{articulo.inventario.stock}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ArticulosReponer;
