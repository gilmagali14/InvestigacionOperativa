import React, { useState, useEffect } from 'react';
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

const Articulos = () => {
  const [articulos, setArticulos] = useState([]);
  const [search, setSearch] = useState('');
  const [filteredArticle, setFilteredArticle] = useState([]);
  const [filter, setFilter] = useState('');
  const [create, setCreate] = useState(false);
  const [showCreate, setShowCreate] = useState(false);
  const [proveedor, setProveedor] = useState(false);
  const [update, setUpdate] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);
  const [idArticulo, setIdArticulo] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const [showSales, setShowSales] = useState(false);
  const [newSaleArticle, setNewSaleArticle] = useState(0);

  const quantityPerPage = 5;

  const filterArticlebyStock = (articulos, filter) => {
    if (filter === 'stock seguridad') {
      const filtered = articulos.filter(
        articulo =>
          articulo.stock + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.stock_seguridad ?? 0)
      );
      return filtered;
    }
    if (filter === 'punto pedido') {
      const filtered = articulos.filter(
        articulo =>
          articulo.stock + (articulo.stock_ingreso_pendiente ?? 0) <=
          (articulo.punto_pedido ?? 0)
      );
      return filtered;
    }
    return articulos;
  };

  const searchArticle = async (search, filter) => {
    const filtered = articulos.filter(articulo =>
      articulo?.nombre?.toLowerCase().includes(search.toLowerCase())
    );
    if (search === '') {
      setFilteredArticle(filterArticlebyStock(articulos, filter));
    }
    setFilteredArticle(filterArticlebyStock(filtered, filter));
  };


  const fetchData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/articulos');
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
      console.log(error)
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
  
    for (let i = 0; i < totalPages; i++) { // Solo mostramos 5 botones de paginación
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
  }, [search, filter]);

  useEffect(() => {
    fetchData();
  }, [create, update]);


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
                className="form-control"
                onChange={(e) => setFilter(e.target.value)}
              >
                <option value="">Filtrar</option>
                <option value="stock seguridad">Stock de Seguridad</option>
                <option value="punto pedido">Punto de Pedido</option>
              </select>
            </div>
          </div>
        
        <div className="">
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
            <AiFillAliwangwang className="me-2" /> Nuevo Artículo
          </button>
        </div>
  
        {showCreate && (
          <CrearArticulo toggleCreate={toggleCreate}/>
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