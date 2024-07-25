import React, { useState } from 'react';

export default function Card({
    articulo,
	  deleteArticle,
	  toggleUpdate,
    toggleProveedor,
	  setIdArticulo,
	  newSale,
}) {
  const [show, setShow] = useState(false);
  const [deleteModal, setDeleteModal] = useState(false);
  
  const update = () => {
    setIdArticulo(articulo.idArticulo);
    toggleUpdate();
  };

  const proveedor = () => {
    setIdArticulo(articulo.idArticulo);
    toggleProveedor();
  };

  return (
    <div className="card m-2 rounded-lg shadow-lg border border-gray-300">
      <div className="card-body d-flex flex-column p-3 bg-gray-100 rounded-top cursor-pointer" onClick={() => setShow(!show)}>
        <div className="d-flex align-items-center justify-content-between mb-2">
        {articulo.fechaBaja != null ? (
          <div>
          <h4 className="card-title fs-5 fw-semibold  text-danger mb-0">{articulo.nombre}</h4>
          <p className="text-danger">(Dado de baja)</p>
          </div>
        ) : (
          <h4 className="card-title fs-5 fw-semibold mb-0">{articulo.nombre}</h4>

        )}
          </div>
          <div>
          <p className="card-title">{articulo.descripcion}</p>
          </div>
          <span className="circle w-4 h-4 rounded-circle ms-2"></span>
        <div className="d-flex align-items-center">
          <button
            className="btn btn-link text-decoration-none text-primary"
            onClick={(e) => {
              e.stopPropagation();
              update();
            }}
          >
            Actualizar
          </button>
          <button
            className="btn btn-link text-decoration-none text-danger"
            onClick={(e) => {
              e.stopPropagation();
              setDeleteModal(true);
            }}
          >
            Dar de baja
          </button>
          <button
            className="btn btn-link text-decoration-none text-primary"
            onClick={(e) => {
              e.stopPropagation();
              newSale(articulo.idArticulo);
            }}
          >
            Cargar venta
          </button>
          <button
            className="btn btn-link text-decoration-none text-primary"
            onClick={(e) => {
              e.stopPropagation();
              proveedor();
            }}
          >
            Asignar proveedor
          </button>
        </div>
      </div>
      {show && (
        <ul class="list-group">
        <li class="list-group-item">Stock: <b>{articulo.stock}</b></li>
        <li class="list-group-item">Precio: <b>${articulo.precio}</b></li>
      </ul>
      )}
      {deleteModal && (
        <div className="modal fixed d-flex align-items-center justify-content-center bg-black bg-opacity-50 z-50 m-6" onClick={() => setDeleteModal(false)}>
          <div className="modal-content bg-white m-6">
            <p className="m-2">¿Estás seguro que deseas eliminar el artículo?</p>
            <div className="d-flex gap-4 w-full">
              <button
                className="btn btn-success m-2"
                onClick={() => deleteArticle(articulo.idArticulo)}
              >
                Sí
              </button>
              <button
                className="btn btn-danger m-2"
                onClick={() => setDeleteModal(false)}
              >
                No
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );

};