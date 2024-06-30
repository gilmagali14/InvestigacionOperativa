import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Home() {
    return (
        <div className="container">
            <div className="row">
                <div className="col">
                    <h1 className="mt-5">Bienvenido</h1>
                </div>
            </div>
            <div className="row mt-4">
                <div className="col">
                    <div className="list-group">
                        <Link to="/crear/articulo" className="list-group-item list-group-item-action">
                            Crear Artículo
                        </Link>
                        <Link to="/articulos" className="list-group-item list-group-item-action">
                            Ver Artículos
                        </Link>
                        <Link to="/demanda" className="list-group-item list-group-item-action">
                            Calcular Demanda
                        </Link>
                        <Link to="/ordenes-de-compra" className="list-group-item list-group-item-action">
                            Ver Órdenes de Compra
                        </Link>
                        <Link to="/validar-stock" className="list-group-item list-group-item-action">
                            Validar Stock
                        </Link>
                        <Link to="/inventarios" className="list-group-item list-group-item-action">
                            Inventario
                        </Link>
                        <Link to="/inventario"  className="list-group-item list-group-item-action">
                            Ver Inventario
                        </Link>
                        <Link to="/actualizar/articulo"  className="list-group-item list-group-item-action">
                            Actualizar articulo
                        </Link>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;
