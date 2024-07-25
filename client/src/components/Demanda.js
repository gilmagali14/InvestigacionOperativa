import React, { useState, useEffect } from 'react';
import axios from 'axios';
import ModalBestOption from "./MejorMetodo";
import PredictionChart from "./PrediccionChart";
import 'react-toastify/dist/ReactToastify.css';
import { IoArrowRedoOutline } from "react-icons/io5";
import { Link } from 'react-router-dom';
import { FaUser } from "react-icons/fa";
import { toast } from 'react-toastify';

const periodNames = {
    mes:'m'
  }
  
export default function Demanda() {

    const [periodName , setPeriodName] = useState("mes");
    const [amounOfPeriods, setAmountOfPeriods] = useState(1);
    const [period, setPeriod] = useState(`${amounOfPeriods}-${periodNames[periodName]}`); 
    const [amontOfCycles, setAmountOfCycles] = useState(1);
    const [cycleName, setCycleName] = useState("meses");
    const [cycle, setCycle] = useState(); 
    const [startDate, setStartDate] = useState("2024-01-01");
    const [endDate, setEndDate] = useState(`${new Date().getFullYear()}-12-31`); 
    const [id, setId] = useState(1);
    const [selectedArticle, setSelectedArticle] = useState();
    const [articulos, setArticulos] = useState([]);
    const [typeOfPrediction, setTypeOfPrediction] = useState("Promedio Movil Suavizado Exponencialmente");
    const [typeOfError, setTypeOfError] = useState("MAD");
    const [allowedError, setAllowedError] = useState(0.1);
    const [error, setError] = useState(0);
    const [nextPeriod, setNextPeriod] = useState(0);
    const [initialValue, setInitialValue] = useState(0);
    const [alfa, setAlfa] = useState(0.2);
    const [backPeriods, setBackPeriods] = useState(3);
    const [weights, setWeights] = useState(Array.from({length: backPeriods}, (_, i) => i + 1));
    const [selectWeights, setSelectWeights] = useState(false);
    const [view, setView] = useState("chart");
    const [prediction, setPredictions] = useState([]);
    const [openModal, setOpenModal] = useState(false);
    const [historicalDemand, setHistoricalDemand] = useState([]);

    const fetchDemanda = async () => {
        let historyBody = {
            "idArticulo": id,
            "tipoPeriodo": "1 mes",
            "fechaInicio": startDate,
            "fechaFin": endDate
        }
        console.log(historyBody)
        const response = await axios.post('http://localhost:8080/demanda-historica', historyBody);
        setHistoricalDemand(response.data)

    }
    const fetchData = async () => {
        let params; 
        
        let historyBody = {
            "idArticulo": id,
            "tipoPeriodo": period + " "+ periodName,
            "fechaInicio": startDate,
            "fechaFin": endDate
        }
        console.log(historyBody)
        const response = await axios.post('http://localhost:8080/demanda-historica', historyBody);
        let demandaHistorica = response.data
        if (typeOfPrediction === "Promedio Movil Suavizado Exponencialmente") {
          params = {
            demandaHistorica: demandaHistorica,
            alfa: alfa,
            valorInicial: initialValue,
            errorMetod: typeOfError,
          }
        } else if (typeOfPrediction === "Promedio Movil Ponderado") {
          params = {
            demandaHistorica: demandaHistorica,
            cantidadPeriodos: {
                cantidadPeriodo: backPeriods,
                peso: weights,
            },
            errorMetod: typeOfError,
          }
        } else if (typeOfPrediction === "Promedio Movil") {
          params = {
            demandaHistorica: demandaHistorica,
            cantidadPeriodos: backPeriods,
            errorMetod: typeOfError,
          }
        } else if (typeOfPrediction === "Regresión Lineal") {
          params = {
            demandaHistorica: demandaHistorica,
            errorMetod: typeOfError,
          }
        }   else if (typeOfPrediction === "Estacionalidad") {
          params = {
            id: id,
            start_date: startDate,
            end_date: endDate,
            period: period,
            cycle: cycle,
            estimatedSales: 200,
          }
        }    
        const predictions = await getPrediction(params);
        setError(predictions.error || 0); // Default to 0 if predictions.error is undefined
        setNextPeriod(predictions.proximoPeriodo || 0); // Default to 0 if predictions.proximoPeriodo is undefined
        setPredictions(predictions || []); // Default to empty array if predictions is undefined
      }

    const getPrediction = async (params) => {
      try {
        if (typeOfPrediction === "Promedio Movil Suavizado Exponencialmente") {
            const response = await axios.post('http://localhost:8080/promedio-movil-ponderado-exp', params);
        return response.data;
        } else if (typeOfPrediction === "Promedio Movil Ponderado") {
            const response = await axios.post('http://localhost:8080/promedio-movil-ponderado', params);
        return response.data;
        } else if (typeOfPrediction === "Promedio Movil") {
          console.log(params)
            const response = await axios.post('http://localhost:8080/promedio-movil', params);
            console.log(response.data)
        return response.data
    } else if (typeOfPrediction === "Regresión Lineal") {
        const response = await axios.post('http://localhost:8080/regresion-lineal', params);
        return response.data;
    } else if (typeOfPrediction === "Estacionalidad") {
        const response = await axios.post(`/api/demanda/estacionalidad`, params);
        return response.data;
    }
  } catch (error) {
    console.log(error.response.data.message)
    toast.error(error.response.data.message)
    return {};
  }
    }
    const getArticulos = async () => {
        const articles = await axios.get("http://localhost:8080/articulos");
        setArticulos(articles.data);
        setId(articles.data[0].idArticulo);
        setSelectedArticle(articles.data[0]);
      }

      useEffect(() => {
        getArticulos();
        fetchDemanda();
      }, []);
    
      useEffect(() => {
        setSelectedArticle(articulos.find((articulo) => articulo.id === id));
      }, [id]);
    
      useEffect(() => {
        setPeriod(`${amounOfPeriods}`);
      }, [amounOfPeriods, periodName]);
    
      useEffect (() => {
        setCycle(`${amontOfCycles}`);
      }, [amontOfCycles, cycleName]);
    
      useEffect(() => {
        setWeights(Array.from({length: backPeriods}, (_, i) => i + 1));
      }, [backPeriods]);
    
 
      useEffect(() => {
        if (typeOfPrediction === "Estacionalidad") {
          setView("tableEst");
        } else {
          setView("chart");
        }
      }, [typeOfPrediction]);

    
      const handleSubmit = async (event) => {
        event.preventDefault();
        try {
          
            fetchData();
        } catch (error) {
        }
      };
      return (
        <div>
        <header className="bg-primary text-white p-3">
          <div className="container d-flex justify-content-between align-items-center">
          <FaUser />
            <h4 className="h3 mb-0">Predicción de demanda</h4>
            <nav>
              <ul className="nav">
                <li className="nav-item">
                  <Link to="/" className="nav-link text-white">Inicio</Link>
                </li>
              </ul>
            </nav>
          </div>
        </header>
        <div className="container mt-4">
          <div className="d-flex flex-column p-4 text-white rounded" style={{ backgroundColor: '#505050' }}>
            <div className="row mb-4">
              <div className="col mx-5">
              <div className="mx-5">

                <form onSubmit={handleSubmit} className="form">
                  <div className="form-group mb-3">
                    <label htmlFor="articulo" className="form-label">
                      Artículo
                    </label>
                    <select
                      name="articulo"
                      id="articulo"
                      onChange={(e) => setId(Number(e.target.value))}
                      required
                      className="form-select"
                    >
                      {articulos.map((articulo) => (
                        <option key={articulo.articuloId} value={articulo.articuloId}>
                          {articulo.nombre}
                        </option>
                      ))}
                    </select>
                  </div>
      
                  <div className="mb-3">
                    <label htmlFor="start" className="form-label">Fecha de inicio</label>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                      <input
                        required
                        type="date"
                        id="start"
                        name="start"
                        value={startDate}
                        onChange={(e) => setStartDate(e.target.value)}
                        className="form-control"
                      />
                    </div>
                    <label htmlFor="end" className="form-label">Fecha de fin</label>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                      <input
                        required
                        type="date"
                        id="end"
                        name="end"
                        value={endDate}
                        onChange={(e) => setEndDate(e.target.value)}
                        className="form-control"
                      />
                    </div>
                    
                    <label htmlFor="period" className="form-label">Periodo</label>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                      <div className="d-flex gap-2">
                        <p className='form-control'>1</p>
                        <select
                            name="periodName"
                            id="periodName"
                            onChange={(e) => {
                              setPeriodName(e.target.value);
                              setAmountOfPeriods(1);
                            }}
                            value={periodName}
                            className="form-select w-100 p-3"
                          >
                            {Object.keys(periodNames).map((key) => (
                              <option key={key} value={key}className="m-3"><p className="m-5">{key}</p></option>
                            ))}
                          </select>

                      </div>
                    </div>
      
                    {typeOfPrediction === "Estacionalidad" && (
                      <div className="d-flex justify-content-between align-items-center mb-2">
                        <label htmlFor="cycle" className="form-label">Ciclo</label>
                        <div className="d-flex gap-2">
                          <input
                            type="number"
                            id="cycle"
                            name="cycle"
                            value={amontOfCycles}
                            onChange={(e) => setAmountOfCycles(Number(e.target.value))}
                            className="form-control"
                          />
                          <select
                            name="cycleName"
                            id="cycleName"
                            onChange={(e) => setCycleName(e.target.value)}
                            value={cycleName}
                            className="form-select"
                          >
                            {Object.keys(periodNames).map((key) => (
                              <option key={key} value={key}>{key}</option>
                            ))}
                          </select>
                        </div>
                      </div>
                    )}
      
                    <label htmlFor="typeOfPrediction" className="form-label">Tipo de Predicción</label>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                      <select
                        required
                        name="typeOfPrediction"
                        id="typeOfPrediction"
                        onChange={(e) => setTypeOfPrediction(e.target.value)}
                        className="form-select"
                      >
                        <option value="Promedio Movil Suavizado Exponencialmente">Promedio Movil Suavizado Exponencialmente</option>
                        <option value="Promedio Movil Ponderado">Promedio Movil Ponderado</option>
                        <option value="Promedio Movil">Promedio Movil</option>
                        <option value="Regresión Lineal">Regresión Lineal</option>
                      </select>
                    </div>
      
                    {typeOfPrediction === "Promedio Movil Suavizado Exponencialmente" && (
                      <>
                        <label htmlFor="alfa" className="form-label">Alfa</label>
                        <div className="d-flex justify-content-between align-items-center mb-2">
                          <input
                            type="number"
                            id="alfa"
                            name="alfa"
                            value={alfa}
                            onChange={(e) => setAlfa(Number(e.target.value))}
                            className="form-control"
                          />
                        </div>
                        <label htmlFor="initialValue" className="form-label">Valor Inicial</label>
                        <div className="d-flex justify-content-between align-items-center mb-2">
                          <input
                            type="number"
                            id="initialValue"
                            name="initialValue"
                            value={initialValue}
                            onChange={(e) => setInitialValue(Number(e.target.value))}
                            className="form-control"
                          />
                        </div>
                      </>
                    )}
      
                    {(typeOfPrediction === "Promedio Movil Ponderado" || typeOfPrediction === "Promedio Movil") && (
                      <div className="d-flex justify-content-between">
                        <div>
                          <label htmlFor="backPeriods" className="form-label">Periodos Atras</label>
                          <div className="d-flex gap-3">
                            <input
                              type="number"
                              id="backPeriods"
                              name="backPeriods"
                              value={backPeriods}
                              min={1}
                              onChange={(e) => setBackPeriods(Number(e.target.value))}
                              className="form-control"
                            />
                            {typeOfPrediction === "Promedio Movil Ponderado" && (
                              <button
                                onClick={() => setSelectWeights(true)}
                                className="btn btn-secondary"
                              >
                                Cambiar Pesos
                              </button>
                            )}
                          </div>
                        </div>
                      </div>
                    )}
      
                    {typeOfPrediction !== "Estacionalidad" && (
                      <>
                        <label htmlFor="typeOfError" className="form-label">Tipo de Error</label>
                        <div className="d-flex justify-content-between align-items-center mb-2">
                          <select
                            name="typeOfError"
                            id="typeOfError"
                            onChange={(e) => setTypeOfError(e.target.value)}
                            className="form-select"
                          >
                            <option value="MAD">MAD</option>
                            <option value="MSE">MSE</option>
                            <option value="MAPE">MAPE</option>
                          </select>
                        </div>
                        <label htmlFor="allowedError" className="form-label">Error Permitido</label>
                        <div className="d-flex justify-content-between align-items-center mb-2 ml-5">
                          <input
                            type="number"
                            id="allowedError"
                            name="allowedError"
                            value={allowedError}
                            onChange={(e) => setAllowedError(Number(e.target.value))}
                            className="form-control"
                          />
                        </div>
                        <div className="d-flex justify-content-start">
                          <div className='mt-4'>
                            <span className={'font-weight-bold p-2 rounded bg-success'}>
                              {typeOfError}: {Math.round(error * 100) / 100} {typeOfError === 'MAPE' ? "%" : ""}
                            </span>
                          </div>
                          <div className='my-4 mx-4'>
                            <span className="font-weight-bold p-2 bg-primary text-white rounded">
                              Predicción siguiente periodo: {nextPeriod}
                            </span>
                          </div>
                        </div>
                      </>
                    )}
                  </div>
                  <div className="d-flex">
                    <button type="submit" className="px-4 py-2 btn btn-light mb-3 d-flex justify-content-center">
                      Calcular demanda
                    </button>
                  </div>
                </form>
              </div>
              </div>
            </div>
            <div className="row"> {/* Fila para el gráfico y el botón */}
              <div className="col">
                <div className="d-flex justify-content-center align-items-center mb-4">
                  <IoArrowRedoOutline />
                  <button
                    className="btn btn-primary mx-3"
                    onClick={() => setOpenModal(true)}
                  >
                    Calcular Mejor Método
                  </button>
                </div>
                <div className="d-flex justify-content-center align-items-center">
                  {prediction.length === 0 ? null : <PredictionChart predictions={prediction} />}
                </div>
              </div>
            </div>
          </div>
      
          {openModal && <ModalBestOption setShow={setOpenModal} historicalDemand={historicalDemand} />}
        </div>
        </div>

      );
    }      