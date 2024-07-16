import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Home() {
    return (
        <div className="">
        <div className="container">
            <div className="row">
                <div className="col">
                    <h1 className="mt-5">Bienvenido</h1>
                </div>
            </div>
            <div className="row m-4">
                <div className="col">
                    <div className="list-group bg-success">
                        <div className="list-group-item bg-light">
                            <i className="bi bi-plus-circle me-2"></i> Maestro de Artículos
                            <ul className="list-group mt-2">
                                <li className="list-group-item list-group-item-action">
                                    <Link to="/crear/articulo" className="text-decoration-none text-dark">
                                    <i className="bi bi-calculator me-2"></i> Crear Articulo
                                    </Link>
                                </li>
                                <li className="list-group-item list-group-item-action">
                                    <Link to="/actualizar/articulo" className="text-decoration-none text-dark">
                                        Actualizar Existente
                                    </Link>
                                </li>
                                <li className="list-group-item list-group-item-action">  
                                <Link to="/articulos" className="text-decoration-none text-dark">
                                Ver Artículos
                                </Link>
                                </li>
                            </ul>
                        </div>
                        <div className="list-group-item list-group-item-action bg-light">
                            <i className="bi bi-plus-circle me-2"></i> Inventario
                            <ul className="list-group mt-2">
                                <li className="list-group-item list-group-item-action">
                                    <Link to="/inventario" className="text-decoration-none text-dark">
                                     Ver Inventario
                                      </Link>
                                </li>
                            </ul>
                        </div>
                        
                        <div className="list-group-item list-group-item-action bg-light">
                            <i className="bi bi-plus-circle me-2"></i> Ordenes de Compra
                            <ul className="list-group mt-2">
                                <li className="list-group-item list-group-item-action">
                                    <Link to="/ordenes-de-compra" className="text-decoration-none text-dark">
                                     Ver Ordenes
                                      </Link>
                                </li>
                                
                            </ul>
                        </div>
                        <div className="list-group-item list-group-item-action bg-light">
                            <i className="bi bi-plus-circle me-2"></i> Demanda
                            <ul className="list-group mt-2">
                                <li className="list-group-item list-group-item-action">
                                    <Link to="/demandas" className="text-decoration-none text-dark">
                                    Ver Demandas Historicas
                                      </Link>
                                </li>
                            <Link to="/demanda" className="list-group-item list-group-item-action">
                            Calcular demanda
                            </Link>
                            </ul>
                        </div>
                        <div className="list-group-item list-group-item-action bg-light">
                            <i className="bi bi-plus-circle me-2"></i> Ventas
                            <ul className="list-group mt-2">
                                <li className="list-group-item list-group-item-action">
                                <Link to="/crear/venta" className="text-decoration-none text-dark">
                            Cargar Venta
                            </Link>
                             
                                </li>
                            </ul>
                        </div>
                      
                    </div>
                </div>
            </div>
        </div>

        </div>
    );
}

export default Home;
