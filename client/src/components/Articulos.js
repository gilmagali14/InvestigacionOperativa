import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import SearchBar from './SearchBar';
import { toast } from 'react-toastify';
import CrearArticulo from './CrearArticulo';
import ActualizarArticulo from './ActualizarArticulo';
import AddArticuloProveedor from './Proveedor';
import Card from './Card';
import { AiFillAliwangwang } from "react-icons/ai";
import { BiLastPage } from "react-icons/bi";
import CrearVenta from './CrearVenta';
import { FaUser } from "react-icons/fa";

const Articulos = () => {
  const [articulos, setArticulos] = useState([]);
  const [search, setSearch] = useState('');
  const [filteredArticle, setFilteredArticle] = useState([]);
  const [filter, setFilter] = useState('');
  const [create, setCreate] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [proveedor, setProveedor] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);
  const [idArticulo, setIdArticulo] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [showSales, setShowSales] = useState(false);
  const [newSaleArticle, setNewSaleArticle] = useState(0);
  const quantityPerPage = 5;

  const filterArticlebyStock = (articulos, filter) => {
    if (filter === 'stock') {
      return articulos.filter(
        articulo =>
          articulo.stock + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.stock_seguridad ?? 0)
      );
    }
    if (filter === 'precio') {
      return articulos.filter(
        articulo =>
          articulo.precio + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.punto_pedido ?? 0)
      );
    }
    return articulos;
  };

  const searchArticle = (search, filter) => {
    let filtered = articulos;

    if (search) {
      filtered = filtered.filter(articulo =>
        articulo?.nombre?.toLowerCase().includes(search.toLowerCase())
      );
    }

    filtered = filterArticlebyStock(filtered, filter);
    setFilteredArticle(filtered);
  };

  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/articulos');
      console.log(response.data);
      setArticulos(response.data);
      setFilteredArticle(response.data);
    } catch (error) {
      console.error('Error fetching data:', error);
      toast.error('Error fetching data');
    }
  };

  const deleteArticle = async (id) => {
    try {
      const response = await axios.delete(`http://localhost:8080/baja/articulo/${id}`);
      toast.success(response.data.message);
      fetchData();
    } catch (error) {
      console.error('Error deleting article:', error);
    }
  };

  const toggleCreate = () => {
    setShowCreate(!showCreate);
  };

  const toggleProveedor = () => {
    setProveedor(!proveedor);
  };

  const toggleUpdate = () => {
    setShowUpdate(!showUpdate);
  };

  const openNewSale = (idArticulo) => {
    setNewSaleArticle(idArticulo);
    setShowSales(true);
  };

  const totalPages = Math.ceil(filteredArticle.length / quantityPerPage);
  const displayedArticles = filteredArticle.slice(
    currentPage * quantityPerPage,
    (currentPage + 1) * quantityPerPage
  );

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
            currentPage === pageNumber - 1
              ? 'btn-primary'
              : 'btn-secondary'
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

  useEffect(() => {
    fetchData();
  }, []);

  useEffect(() => {
    searchArticle(search, filter);
  }, [search, filter, articulos]);

  useEffect(() => {
    if (create) {
      fetchData();
    }
  }, [create]);

  return (
    <div>
      <header className="bg-primary text-white p-3">
        <div className="container d-flex justify-content-between align-items-center">
        <FaUser />
          <h4 className="h3 mb-0">Artículos</h4>
          <nav>
            <ul className="nav">
              <li className="nav-item">
                <Link to="/" className="nav-link text-white">Inicio</Link>
              </li>
            </ul>
          </nav>
        </div>
      </header>
      <div style={{ height: '80px' }} className='container mt-5'>
        <div className="d-flex align-items-center justify-content-end">
          <SearchBar setSearch={setSearch} />
       
        </div>

        <div>
          {displayedArticles.map((articulo) => (
            <Card
              key={articulo.idArticulo}
              articulo={articulo}
              setIdArticulo={setIdArticulo}
              deleteArticle={deleteArticle}
              toggleUpdate={toggleUpdate}
              toggleProveedor={toggleProveedor}
              newSale={openNewSale}
            />
          ))}
        </div>

        <div className="fixed-bottom d-flex justify-content-end m-3">
          <button
            className="btn btn-primary rounded-pill shadow-sm"
            onClick={toggleCreate}
          >
            <AiFillAliwangwang className="me-2" /> Crear artículo
          </button>
        </div>

        {showCreate && (
          <CrearArticulo toggleCreate={toggleCreate} />
        )}

        {proveedor && (
          <AddArticuloProveedor toggleProveedor={toggleProveedor} id={idArticulo} />
        )}

        {showUpdate && (
          <ActualizarArticulo toggleUpdate={toggleUpdate} id={idArticulo} />
        )}

        <div className='m-3 d-flex align-items-center justify-content-center'>
          <BiLastPage
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

        {showSales && (
          <CrearVenta setShowModal={setShowSales} id={newSaleArticle} />
        )}
      </div>
    </div>
  );
};

export default Articulos;
