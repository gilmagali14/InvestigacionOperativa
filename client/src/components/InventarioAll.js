import React, { useState, useEffect } from 'react';
import axios from 'axios';
import SearchBar from './SearchBar';
import { toast } from 'react-toastify';
import { BiLastPage } from "react-icons/bi";
import { MdOutlineFirstPage } from "react-icons/md";

const Articulos = () => {
  const [articulo, setArticulos] = useState([]);
  const [search, setSearch] = useState('');
  const [filteredArticle, setFilteredArticle] = useState([]);
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
      setArticulos(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      toast.error('Error fetching data');
    }
  };

  const totalPages = Math.ceil(filteredArticle.length / quantityPerPage);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  const getPaginationButtons = () => {
    const buttons = [];
    let startPage = currentPage;

    if (currentPage + 4 > totalPages) {
      startPage = totalPages - 4;
    }

    startPage = Math.max(startPage, 0);

    for (let i = 0; i < totalPages; i++) {
      const pageNumber = startPage + i + 1;
      buttons.push(
        <button
          key={i}
          className={`btn btn-sm mx-1 ${
            currentPage === pageNumber - 1 ? 'btn-primary' : 'btn-secondary'
          }`}
          onClick={() => handlePageChange(pageNumber - 1)}
          disabled={pageNumber > totalPages}
        >
          {pageNumber}
        </button>
      );
    }
    return buttons;
  };

  const filterByStock = async (articles) => {
    if (filter === 'reponer') {
      try {
        const data = await fetchReponer();
        return data;
      } catch (error) {
        toast.error('Error fetching data');
        return [];
      }
    }
    if (filter === 'faltantes') {
      return articles.filter(art =>
        art.articuloProveedor && art.articuloProveedor.some(prov => art.stock < prov.stockSeguridad)
      );
    }
    return articles;
  };

  const fetchReponer = async () => {
    try {
      const response = await axios.get('http://localhost:8080/articulos-a-reponer');
      return response.data;
    } catch (error) {
      throw new Error('Error fetching data');
    }
  };

  useEffect(() => {
    const fetchDataAndFilter = async () => {
      await fetchData();
      let filteredArticles = articulo;

      if (filter !== '') {
        filteredArticles = await filterByStock(articulo);
      }

      setFilteredArticle(filteredArticles);
    };

    fetchDataAndFilter();
  }, [filter, articulo]);

  useEffect(() => {
    const searchFilteredArticles = () => {
      const filtered = articulo.filter(art =>
        art.nombreArticulo.toLowerCase().includes(search.toLowerCase())
      );
      filterByStock(filtered).then(filtered => setFilteredArticle(filtered));
    };

    if (search !== '') {
      searchFilteredArticles();
    } else {
      filterByStock(articulo).then(filtered => setFilteredArticle(filtered));
    }
  }, [search, articulo]);

  return (
    <div className="container">
      <div style={{ height: '80px' }}>
        <div className="col-12 col-md-6">
          <h1 className="text-center display-4 font-weight-bold mb-4">Artículos</h1>
        </div>
        <div className="d-flex align-items-center justify-content-end">
          <SearchBar setSearch={setSearch} />
          <div className="form-group d-flex flex-grow-1 ml-2">
            <select
              className="form-control m-1"
              onChange={(e) => setFilter(e.target.value)}
            >
              <option className="m-1" value="">Filtrar</option>
              <option className="m-1 p-3" value="reponer">Articulos a reponer</option>
              <option className="m-1 p-3" value="tipos">Tipo de articulo</option>
              <option className="m-1 p-3" value="faltantes">Articulos faltantes</option>
            </select>
          </div>
        </div>
        <div className='container mt-5'>
          {filteredArticle.map((art, index) => (
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
                                <table className="table table-striped">
                                  <thead>
                                    <tr>
                                      <th scope="col" className="text-center">Tiempo de entrega</th>
                                      <th scope="col" className="text-center">Costo de pedido</th>
                                      <th scope="col" className="text-center">Modelo</th>
                                      <th scope="col" className="text-center">Stock de seguridad</th>
                                      <th scope="col" className="text-center">Lote optimo</th>
                                      <th scope="col" className="text-center">Punto de pedido</th>
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
                                    </tr>
                                  </tbody>
                                </table>
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
            className={`w-6 h-6 ${
              currentPage === 0 ? 'text-gray-300' : 'text-black cursor-pointer hover:text-black'
            }`}
            onClick={() => currentPage > 0 && handlePageChange(currentPage - 1)}
          />
          {getPaginationButtons()}
          <BiLastPage
            className={`w-6 h-6 ${
              currentPage === totalPages - 1
                ? 'text-gray-300'
                : 'text-black cursor-pointer hover:text-black'
            }`}
            onClick={() =>
              currentPage < totalPages - 1 && handlePageChange(currentPage + 1)
            }
          />
        </div>
      </div>
    </div>
  );
};

export default Articulos;
