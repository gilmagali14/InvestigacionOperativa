import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function Home() {
    return (
        <div className="container">
            <h1 className="mt-5">Bienvenido</h1>
            <nav className="mt-3">
                <ul className="nav">
                    <li className="nav-item">
                        <Link to="/crear/articulo" className="nav-link">Crear Artículo</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/crear/orden-de-compra" className="nav-link">Crear Orden de Compra</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/articulos" className="nav-link">Ver Artículos</Link>
                    </li>
                    <li className="nav-item">
                        <Link to="/demanda" className="nav-link">Calcular demanda</Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
}

export default Home;
