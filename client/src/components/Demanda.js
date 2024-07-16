import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

const DemandaComponent = () => {
  const options = [
    { value: 'Promedio Movil Ponderado', label: 'Promedio Movil Ponderado'}
  ];

  const [selectedOption, setSelectedOption] = useState('');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [idArticulo, setIdArticulo] = useState(null); 
  const [demandaCalculada, setDemandaCalculada] = useState(null); 
  const navigate = useNavigate();

  const handleChange = (event) => {
    setSelectedOption(event.target.value);
  };

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const handleCalculateDemand = async () => {
    try {
      const demandaData = {
        fechaDesde: startDate,
        fechaHasta: endDate,
        idArticulo: idArticulo
      };
      console.log(demandaData)
      const response = await axios.post('http://localhost:8080/calcular-demanda', demandaData);
      const demandaCalculada = response.data;
      setDemandaCalculada(demandaCalculada);
      navigate(`/crear/orden-de-compra/${demandaCalculada}`); 
    } catch (error) {
      console.error('Error al calcular la demanda:', error);
    }
  };

  const [articulos, setArticulos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
      
        const response = await fetch('http://localhost:8080/articulos');
        const data = await response.json();
        setArticulos(data);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    
    fetchData();
  }, []);

  const handleArticuloSelection = (id) => {
    setIdArticulo(id);
  };

  return (
    <div className="container">
      <h2 className="my-4">Calcular demanda</h2>
      <table className="table">
        <thead>
          <tr>
            <th scope="col">Artículo</th>
            <th scope="col">Codigo</th>
            <th scope="col">Nombre</th>
          </tr>
        </thead>
        <tbody>
          {articulos.map(articulo => (
            <tr key={articulo.idArticulo}>
              <td><input type="checkbox" onChange={() => handleArticuloSelection(articulo.idArticulo)} /></td>
              <td>{articulo.idArticulo}</td>
              <td>{articulo.nombre}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="mb-3">
        <label htmlFor="calculoSelect" className="form-label">Seleccione el método:</label>
        <select
          id="calculoSelect"
          className="form-select"
          value={selectedOption}
          onChange={handleChange}
        >
          <option value="" disabled>Seleccionar</option>
          {options.map(option => (
            <option key={option.value} value={option.value}>{option.label}</option>
          ))}
        </select>
      </div>
      <div className="mb-3">
        <label htmlFor="startDate" className="form-label">Desde:</label>
        <input
          type="date"
          className="form-control"
          id="startDate"
          value={startDate}
          onChange={handleStartDateChange}
        />
      </div>
      <div className="mb-3">
        <label htmlFor="endDate" className="form-label">Hasta:</label>
        <input
          type="date"
          className="form-control"
          id="endDate"
          value={endDate}
          onChange={handleEndDateChange}
        />
      </div>
      <button type="button" className="btn btn-primary" onClick={handleCalculateDemand}>Calcular demanda</button>
      <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
    </div>
  );
};

export default DemandaComponent;
