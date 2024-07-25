import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SearchBar from './SearchBar';
import { toast } from 'react-toastify';
import { BiLastPage } from "react-icons/bi";
import { MdOutlineFirstPage } from "react-icons/md";
import { Link } from 'react-router-dom';
import { FaUser } from "react-icons/fa";

const Articulos = () => {
  const [articulos, setArticulos] = useState([]);
  const [search, setSearch] = useState('');
  const [filteredArticles, setFilteredArticles] = useState([]);
  const [filter, setFilter] = useState('');
  const [currentPage, setCurrentPage] = useState(0);
  const [activeAccordion, setActiveAccordion] = useState(null);
  const quantityPerPage = 5;

  const toggleAccordion = (index) => {
    setActiveAccordion(activeAccordion === index ? null : index);
  };

  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/articulos/proveedores');
      console.log(response.data)
      setArticulos(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      toast.error('Error fetching data');
    }
  };

  const filterByStock = async (articles) => {
    console.log(filter)
    if (filter === 'reponer') {
      try {

        const response = await axios.get('http://localhost:8080/articulos-a-reponer');
        console.log(response.data)

        return response.data;
      } catch (error) {
        toast.error('Error fetching data');
        return [];
      }
    }

    if (filter === 'faltantes') {
      console.log(articles);
      return articles.filter(art =>
      
        art.articuloProveedor && art.articuloProveedor.some(prov => art.stock < prov.stockSeguridad)
      );
    }

    if (filter === 'intervaloFijo') {
      return articles.filter(art =>
        art.articuloProveedor && art.articuloProveedor.some(prov => prov.modelo === 'intervalo-fijo')
      );
    }
    if (filter === 'loteFijo') {
      return articles.filter(art =>
        art.articuloProveedor && art.articuloProveedor.some(prov => prov.modelo === 'lote-fijo')
      );
    }
    return articles;
  };

  useEffect(() => {
    const fetchDataAndFilter = async () => {
      await fetchData();
    };

    fetchDataAndFilter();
  }, []);

  useEffect(() => {
    const applyFiltersAndSearch = async () => {
      let filtered = articulos;

      if (filter) {
        filtered = await filterByStock(filtered);
      }

      if (search) {
        filtered = filtered.filter(art =>
          art.nombreArticulo.toLowerCase().includes(search.toLowerCase())
        );
      }

      setFilteredArticles(filtered);
      setCurrentPage(0); 
    };

    applyFiltersAndSearch();
  }, [search, filter, articulos]);

  const totalPages = Math.ceil(filteredArticles.length / quantityPerPage);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  const getPaginationButtons = () => {
    const buttons = [];
    let startPage = Math.max(currentPage - 2, 0);
    let endPage = Math.min(startPage + 4, totalPages - 1);

    if (endPage - startPage < 4) {
      startPage = Math.max(endPage - 4, 0);
    }

    for (let i = startPage; i <= endPage; i++) {
      buttons.push(
        <button
          key={i}
          className={`btn btn-sm mx-1 ${currentPage === i ? 'btn-primary' : 'btn-secondary'}`}
          onClick={() => handlePageChange(i)}
        >
          {i + 1}
        </button>
      );
    }
    return buttons;
  };

  const currentArticles = filteredArticles.slice(currentPage * quantityPerPage, (currentPage + 1) * quantityPerPage);

  return (
    <div>
    <header className="bg-primary text-white p-3">
      <div className="container d-flex justify-content-between align-items-center">
      <FaUser />
        <h4 className="h3 mb-0">Inventario</h4>
        <nav>
          <ul className="nav">
            <li className="nav-item">
              <Link to="/" className="nav-link text-white">Inicio</Link>
            </li>
          </ul>
        </nav>
      </div>
    </header>
    <div className="container mt-5">
      <div style={{ height: '80px' }}>
        <div className="d-flex align-items-center justify-content-end">
          <SearchBar setSearch={setSearch} />
          <div className="form-group d-flex flex-grow-1 ml-2">
            <select
              className="form-control m-1"
              onChange={(e) => setFilter(e.target.value)}
              value={filter}
            >
              <option className='p-2' value="">Filtrar</option>
              <option className='p-2' value="reponer">Articulos a reponer</option>
              <option className='p-2' value="faltantes">Articulos faltantes</option>
              <option className='p-2' value="intervaloFijo">Intervalo fijo</option>
              <option className='p-2' value="loteFijo">Lote fijo</option>

            </select>
          </div>
        </div>
        <div className='container mt-5'>
          {currentArticles.map((art, index) => (
            <div id={`accordion-${index}`} key={index}>
              <div className="card">
                <div
                  className={`card-header ${activeAccordion === index ? '' : 'collapsed'}`}
                  onClick={() => toggleAccordion(index)}
                  aria-expanded={activeAccordion === index}
                  aria-controls={`collapse-${index}`}
                  id={`heading-${index}`}
                >
                  <h5 className='m-2'><b>{art.nombreArticulo}</b></h5>
                  <p className='m-2'>{art.descripcion}</p>
                </div>
                <div
                  id={`collapse-${index}`}
                  className={`collapse ${activeAccordion === index ? 'show' : ''}`}
                  aria-labelledby={`heading-${index}`}
                  data-parent={`#accordion-${index}`}
                >
                  <div className="card-body">
                    <ul className="list-group">
                      <li className="list-group-item"><b>Precio:</b> ${art.precio}</li>
                      <li className="list-group-item"><b>Stock:</b> {art.stock}</li>
                      <li className="list-group-item"><b>Tipo de articulo:</b> {art.tipoArticulo}</li>

                      {art.articuloProveedor ? (
                        <div>
                          {art.articuloProveedor.map((a, subIndex) => (
                            <div key={subIndex}>
                              <li className="list-group-item">
                                <p><b>Proveedor:</b> {a.proveedor}</p>
                                {a.modelo == 'lote-fijo' ? (
                                <table className="table table-striped">
                                  <thead>
                                    <tr>
                                      <th scope="col" className="text-center">Tiempo de entrega</th>
                                      <th scope="col" className="text-center">Costo de pedido</th>
                                      <th scope="col" className="text-center">Modelo</th>
                                      <th scope="col" className="text-center">Stock de seguridad</th>
                                      <th scope="col" className="text-center">Lote optimo</th>
                                      <th scope="col" className="text-center">Punto de pedido</th>
                                      <th scope="col" className="text-center">CGI</th>

                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                      <td className="text-center">{a.tiempoEntrega}</td>
                                      <td className="text-center">{a.costoPedido}</td>
                                      <td className="text-center">{a.modelo}</td>
                                      <td className="text-center">{a.stockSeguridad}</td>
                                      <td className="text-center">{a.loteOptimo}</td>
                                      <td className="text-center">{a.puntoPedido}</td>
                                      <td className="text-center">{a.cgi}</td>

                                    </tr>
                                  </tbody>
                                </table>
                                ) : (
                                  <table className="table table-striped">
                                  <thead>
                                    <tr>
                                      <th scope="col" className="text-center">Tiempo de entrega</th>
                                      <th scope="col" className="text-center">Costo de pedido</th>
                                      <th scope="col" className="text-center">Modelo</th>
                                      <th scope="col" className="text-center">Stock de seguridad</th>
                                      <th scope="col" className="text-center">Lote optimo</th>
                                      <th scope="col" className="text-center">CGI</th>

                                    </tr>
                                  </thead>
                                  <tbody>
                                    <tr>
                                      <td className="text-center">{a.tiempoEntrega}</td>
                                      <td className="text-center">{a.costoPedido}</td>
                                      <td className="text-center">{a.modelo}</td>
                                      <td className="text-center">{a.stockSeguridad}</td>
                                      <td className="text-center">{a.loteOptimo}</td>
                                      <td className="text-center">{a.cgi}</td>

                                    </tr>
                                  </tbody>
                                </table>
                                )
                              }
                              </li>
                            </div>
                          ))}
                        </div>
                      ) : (
                        <div>No hay proveedores disponibles</div>
                      )}
                    </ul>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
        <div className='m-3 d-flex align-items-center justify-content-center'>
          <MdOutlineFirstPage
            className={`w-6 h-6 ${currentPage === 0 ? 'text-gray-300' : 'text-black cursor-pointer hover:text-black'}`}
            onClick={() => currentPage > 0 && handlePageChange(currentPage - 1)}
          />
          {getPaginationButtons()}
          <BiLastPage
            className={`w-6 h-6 ${currentPage === totalPages - 1 ? 'text-gray-300' : 'text-black cursor-pointer hover:text-black'}`}
            onClick={() => currentPage < totalPages - 1 && handlePageChange(currentPage + 1)}
          />
        </div>
      </div>
    </div>
    </div>

  );
};

export default Articulos;
