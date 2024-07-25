import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import { AiFillProduct } from "react-icons/ai";
import { MdInventory } from "react-icons/md";
import { MdOutlineBorderColor } from "react-icons/md";
import { FaUser } from "react-icons/fa";
import { IoCalculator } from "react-icons/io5";
import '../Home.css'; 

function Home() {
    return (
        <div className="d-flex flex-column min-vh-100">

        <header className="bg-primary text-white p-3">
        <div className="container d-flex justify-content-between align-items-center">
        <FaUser />
          <h4 className="h3 mb-0">Bienvenido</h4>
          <nav>
            <ul className="nav">
              <li className="nav-item">
                <Link to="/" className="nav-link text-white">Inicio</Link>
              </li>
            </ul>
          </nav>
        </div>
      </header>
    <div className='container bg-light rounded'>
            <div className="row">
                <div className="col m-4">
                    <Link to="/articulos" className="link-box pastel-blue">
                        <div className="icon-text">
                            <i className="bi bi-calculator me-2"></i><b>Maestro Artículo</b>
                        </div>
                        <AiFillProduct className="icon-size"/>
                    </Link>
                </div>
                <div className="col m-4">
                <Link to="/demanda" className="link-box pastel-blue">
                        <div className="icon-text">
                            <i className="bi bi-calculator me-2"></i> <b>Predicción de Demanda</b>
                        </div>
                        <IoCalculator className='icon-size'/>
                    </Link>
                </div>
            </div>
            <div className="row">
                <div className="col m-4">
                    <Link to="/ordenes-de-compra" className="link-box pastel-blue">
                        <div className="icon-text">
                            <i className="bi bi-calculator me-2"></i> <b>Ordenes de Compra</b>
                        </div>
                        <MdOutlineBorderColor className="icon-size"/>
                    </Link>
                </div>
                <div className="col m-4">
                    <Link to="/inventario" className="link-box pastel-blue">
                        <div className="icon-text">
                            <i className="bi bi-calculator me-2"></i> <b>Inventario</b>
                        </div>
                        <MdInventory className="icon-size"/>
                    </Link>
                </div>
            </div>
            </div>
            </div>

);
}

export default Home;
