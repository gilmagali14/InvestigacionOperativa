import React, { useState, useEffect } from 'react';
import axios from 'axios';

const periodNames = {
    días:'d',
    semanas: 'w',
    meses:'m',
    años: 'y',
  }
  
export default function Demanda() {

    const [data , setData] = useState([]);
    const [periodName , setPeriodName] = useState("meses");
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
    const [formatedData, setFormatedData] = useState([]);
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
    const [cycles, setCycles] = useState([]);
    const [averageSalesByPeriod, setAverageSalesByPeriod] = useState([]);
    const [seasonalIndex, setSeasonalIndex] = useState([]);
    const [predictions, setPredictions] = useState([]);
    const [openModal, setOpenModal] = useState(false);
    const [bestMetod, setBestMetod] = useState("Mejor Metodo...");
    const [historicalDemand, setHistoricalDemand] = useState([]);

    const fetchData = async () => {
        let params; 
        
        let historyBody = {
            "idArticulo": id,
            "tipoPeriodo": period + " mes",
            "fechaInicio": startDate,
            "fechaFin": endDate
        }
        console.log(historyBody)
        const response = await axios.post('http://localhost:8080/demanda-historica', historyBody);
        console.log(response.data)
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

        console.log("PARAMETROS", params)
        if (typeOfPrediction === "Estacionalidad") {
          const response = await axios.get(`/api/demanda/estacionalidad`, params);
          setCycles(response.data.cyclesWithPeriods);
          setAverageSalesByPeriod(response.data.averageSalesByPeriod);
          setSeasonalIndex(response.data.seasonalIndex);
          setPredictions(response.data.predictions);
          
          return;
        }
    
        const predictions = await getPrediction(params);
        console.log("PROBANDO", predictions)
        setError(predictions.error);
        setNextPeriod(predictions.proximoPeriodo);
        predictions.prediccion.map ((item) => {
          return item.valor;
        });

      }

    const getPrediction = async (params) => {
        if (typeOfPrediction === "Promedio Movil Suavizado Exponencialmente") {
            console.log(params)
            const response = await axios.post('http://localhost:8080/promedio-movil-ponderado-exp', params);
        return response.data;
        } else if (typeOfPrediction === "Promedio Movil Ponderado") {
            const response = await axios.post('http://localhost:8080/promedio-movil-ponderado', params);
        return response.data;
        } else if (typeOfPrediction === "Promedio Movil") {
            console.log(params)
            const response = await axios.post('http://localhost:8080/promedio-movil', params);
        console.log(params)
        console.log(response.data)    

        return response.data
    } else if (typeOfPrediction === "Regresión Lineal") {
        const response = await axios.post('http://localhost:8080/regresion-lineal', params);
        return response.data;
        } else if (typeOfPrediction === "Estacionalidad") {
        const response = await axios.post(`/api/demanda/estacionalidad`, params);
        return response.data;
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
    
   /*    useEffect(() => {
        fetchData();
      }, [id, startDate, endDate, period, typeOfPrediction, typeOfError , allowedError, alfa, initialValue, backPeriods, cycle, weights]);
     */
      useEffect(() => {
        //if the type of prediction is estacionalidad, then we need to change the view
        if (typeOfPrediction === "Estacionalidad") {
          setView("tableEst");
        } else {
          setView("chart");
        }
      }, [typeOfPrediction]);
    
      useEffect(() => {
        setBestMetod("Mejor Metodo...");
      }, [historicalDemand]);
    
      const handleSubmit = async (event) => {
        event.preventDefault();
        try {
          
            fetchData();
        } catch (error) {
        }
      };
      return (
        <div className="flex flex-col items-center justify-center gap-4 p-4 w-full h-full bg-danger">
          <h1 className="text-lg ">
              Historial de Ventas de
              { selectedArticle && <span className="text-lg font-semibold"> {selectedArticle.nombre}</span>}
          </h1>
          <div className="flex items-center justify-between p-4 bg-gray-700 shadow-lg w-full h-full text-white">
            <div>
              <div className="flex flex-col items-start h-full w-1/2">
              <form onSubmit={handleSubmit}>
                <div className="flex flex-col items-start gap-4 controles  p-4 rounded-lg">
                  <span className="flex gap-2 items-center font-semibold"> 
                    <p className="h-6 w-6" />
                    Articulo
                  </span>
                  <select name="articulo" id="articulo" onChange={(e) => setId(Number(e.target.value))} required
                    className="text-black focus:outline-none focus:ring-none focus:border-transparent rounded-full py-1 px-2">
                    {articulos.map((articulo) => (
                      <option key={articulo.id} value={articulo.id}>
                        {articulo.nombre}
                      </option>
                    ))}
                  </select>
                  <div className="flex flex-col justify-start items-start w-full gap-2">
                    <div className="flex gap-2 items-center w-full justify-between">
                      <label htmlFor="start">Fecha de inicio</label>
                      <input 
                      required
                        type="date" id="start" 
                        name="start" 
                        value={startDate} 
                        onChange={(e) => setStartDate(e.target.value)} 
                        className="text-black rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                      />
                    </div>
                    <div className="flex gap-2 items-center w-full justify-between">
                      <label htmlFor="end">Fecha de fin </label>
                      <input 
                      required
                        className="text-black rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                        type="date" 
                        id="end" 
                        name="end" 
                        value={endDate} 
                        onChange={(e) => setEndDate(e.target.value)} 
                      />
                    </div>
                    <div className="flex gap-2 items-center w-full justify-between">
                      <label htmlFor="period">Periodo</label>
                      <div className="flex gap-1">
    
                        <input 
                        required
                          type="number" 
                          id="period" 
                          name="period" 
                          value={amounOfPeriods} 
                          onChange={(e) => setAmountOfPeriods(Number(e.target.value))} 
                          className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                        />
                        <select 
                          name="periodName" 
                          id="periodName" 
                          onChange={(e) => setPeriodName(e.target.value)} 
                          value={periodName}
                          className="text-black w-20 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                        >
                          {Object.keys(periodNames).map((key) => (
                            <option key={key} value={key}>{key}</option>
                          ))}
                        </select>
                      </div>
                    </div>
                    {
                      typeOfPrediction === "Estacionalidad" &&
                      <div className="flex gap-2 items-center w-full justify-between">
                        <label htmlFor="period">Ciclo</label>
                        <div className="flex gap-1">
    
                          <input 
                            type="number" 
                            id="period" 
                            name="period" 
                            value={amontOfCycles} 
                            onChange={(e) => setAmountOfCycles(Number(e.target.value))} 
                            className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                          />
                          <select 
                            name="periodName" 
                            id="periodName" 
                            onChange={(e) => setCycleName(e.target.value)} 
                            value={cycleName}
                            className="text-black w-20 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                          >
                            {Object.keys(periodNames).map((key) => (
                              <option key={key} value={key}>{key}</option>
                            ))}
                          </select>
                        </div>
                      </div>
                    }
                    <div className="flex gap-2 items-center w-full justify-between">
                      <label htmlFor="typeOfPrediction">Tipo de Predicción</label>
                      <select 
                      required
                        name="typeOfPrediction" 
                        id="typeOfPrediction" 
                        onChange={(e) => setTypeOfPrediction(e.target.value)} 
                        className="text-black w-60 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                      >
                        <option value="Promedio Movil Suavizado Exponencialmente">Promedio Movil Suavizado Exponencialmente</option>
                        <option value="Promedio Movil Ponderado">Promedio Movil Ponderado</option>
                        <option value="Promedio Movil">Promedio Movil</option>
                        <option value="Regresión Lineal">Regresión Lineal</option>
                       {/* ¨<option value="Estacionalidad">Estacionalidad</option> */}
                      </select>
                    </div>
                    <div className="flex gap-2 items-center w-full justify-between">
                      {
                        typeOfPrediction === "Promedio Movil Suavizado Exponencialmente" &&
                        <div className="flex gap-2 items-center w-full justify-between">
                          <label htmlFor="alfa">Alfa</label>
                          <input 
                            type="number" 
                            id="alfa" 
                            name="alfa" 
                            value={alfa} 
                            onChange={(e) => setAlfa(Number(e.target.value))} 
                            className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                          />
                        </div>
                      }
                      {
                        typeOfPrediction === "Promedio Movil Suavizado Exponencialmente" &&
                        <div className="flex gap-2 items-center w-full justify-between">
                          <label htmlFor="initialValue">Valor Inicial</label>
                          <input 
                            type="number" 
                            id="initialValue" 
                            name="initialValue" 
                            value={initialValue} 
                            onChange={(e) => setInitialValue(Number(e.target.value))} 
                            className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                          />
                        </div>
                      }
                      {
                        (typeOfPrediction === "Promedio Movil Ponderado" || typeOfPrediction === "Promedio Movil") &&
                        <div className="flex gap-2 items-center w-full justify-between">
                          <label htmlFor="backPeriods">Periodos Atras</label>
                          <input 
                            type="number" 
                            id="backPeriods" 
                            name="backPeriods" 
                            value={backPeriods} 
                            min={1}
                            onChange={(e) => setBackPeriods(Number(e.target.value))} 
                            className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                          />
                          {
                            typeOfPrediction === "Promedio Movil Ponderado" &&
                            < button onClick={() => setSelectWeights(true)} className="bg-contrast p-2 rounded-lg">Cambiar Pesos</button>
                          }
                        </div>
                      }
                    </div>
                    {
                      typeOfPrediction !== "Estacionalidad" &&
                      <div className="flex gap-2 items-center w-full justify-between">
                        <label htmlFor="typeOfError">Tipo de Error</label>
                        <select 
                          name="typeOfError" 
                          id="typeOfError" 
                          onChange={(e) => setTypeOfError(e.target.value)} 
                          className="text-black w-60 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent"
                        >
                          <option value="MAD">MAD</option>
                          <option value="MSE">MSE</option>
                          <option value="MAPE">MAPE</option>
                        </select>
                      </div>
                    }
                    {
                      typeOfPrediction !== "Estacionalidad" &&
                      <div className="flex gap-2 items-center w-full justify-between">
                        <label htmlFor="allowedError">Error Permitido</label>
                        <input 
                          type="number" 
                          id="allowedError" 
                          name="allowedError" 
                          value={allowedError} 
                          onChange={(e) => setAllowedError(Number(e.target.value))} 
                          className="w-16 rounded-lg py-1 px-2 focus:outline-none focus:ring-none focus:border-transparent text-black"
                        />
                      </div>
                    }
                    {
                      typeOfPrediction !== "Estacionalidad" &&
                      <div className="flex gap-2 items-center w-full justify-between">
                            <span className={`font-semibold p-2 rounded-lg}`} >
                              {typeOfError}: {Math.round(error*100)/100 } { typeOfError == 'MAPE' ? "%" : null }
                              </span>
                            <span className="font-semibold p-2 bg-blue-500 rounded-lg">Predicción siguiente periodo: {nextPeriod}</span>
                      </div>
                    }
                  </div>
                </div>
                <button type="submit" className="btn btn-success">
                  Crear Venta
                </button>
                </form>
              </div>
    
              <div className="flex flex-col items-start gap-4 p-4 rounded-lg border border-gray-400">
                <div className="flex align-center justify-arround gap-6">
                  <h3 className="border border-gray-400 p-2 rounded-lg">
                    El mejor metodo es : {bestMetod}
                  </h3>
                </div>
                <button className="bg-blue-500 p-2 rounded-lg text-white" onClick={() => setOpenModal(true)}>Calcular Mejor Metodo</button>
              </div>
    
            </div>
            <div className="flex flex-col items-start justify-center h-full gap-4 w-1/2">
            
              {typeOfPrediction !== "Estacionalidad" && 
                <div className="flex items-center">
                  <div className="bg-gray-200 rounded-lg p-2 flex relative w-40">
                    <div
                      className={`absolute top-1 bottom-1 rounded-lg bg-stone-500 transition-transform duration-300 ease-in-out ${
                        view === "chart" ? "translate-x-0" : "translate-x-full"
                      }`}
                      style={{ width: '46%' }}
                    ></div>
                    <button
                      onClick={() => setView("chart")}
                      className={`px-4 py-2 rounded-lg z-10 focus:outline-none w-1/2 ${view === "chart" ? "text-white" : "text-black"}`}
                    >
                      Grafica
                    </button>
                    <button
                      onClick={() => setView("table")}
                      className={`px-4 py-2 rounded-lg z-10 focus:outline-none w-1/2 ${view === "table" ? "text-white" : "text-black"}`}
                    >
                      Tabla
                    </button>
                  </div>
                </div>
    
              }
            </div>
           
          </div>
          
        </div>
      );
    }