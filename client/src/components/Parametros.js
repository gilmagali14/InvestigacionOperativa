import React, { useState, useEffect } from 'react';
import axios from 'axios';

const GeneralDemandParameters = () => {
    const [params, setParams] = useState({
        periodosAPredecir: 0,
        metodoCalculoError: "",
        errorAceptable: 0
    });
    const obtenerParametrosGenerales = async () => {
        try {
            const response = await fetch('/api/generalParameters');
            if (!response.ok) {
                throw new Error('Error al obtener los parámetros generales');
            }
            const data = await response.json();
        } catch (error) {
            console.error('Error:', error);
        }
    };

    useEffect(() => {
        obtenerParametrosGenerales();
    }, []);

    const guardar = async () => {
        try {
            console.log(params)
            const response = await axios.post('http://localhost:8080/generalParameters', {
            });
            if (!response.ok) {
                throw new Error('Error al guardar los parámetros generales');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setParams({ ...params, [name]: value });
    };
    return (
        <div>
            <div className="d-flex flex-row justify-content-between mb-2">
                <span>Periodos a predecir</span>
                <input
                    id="periodosAPredecir"
                    name="periodosAPredecir"
                    type="number"
                    min={0}
                    onChange={handleChange}
                />
            </div>

            <div className="mb-3 w-100">
                <label htmlFor="metodoCalculoError" className="form-label">Método de Cálculo de Error  </label>
                <select className="form-select"
                    id="metodoCalculoError"
                    name="metodoCalculoError"
                    onChange={handleChange}
                    required
                >
                    <option>Seleccionar</option>
                    <option value="MSE">Error Medio Cuadrado</option>
                    <option value="MAD">Error Medio Absoluto</option>
                    <option value="MAPE">Error Medio Porcentual Absoluto</option>
                </select>
            </div>
            <div className="d-flex flex-row justify-content-between mb-2">
                <span>Error aceptable</span>
                <input
                    id='errorAceptable'
                    name="errorAceptable"

                    type="number"
                    min={0}
                    onChange={handleChange}
                />
            </div>

            <div className="d-flex flex-row justify-content-end mb-2">
                <button className="bg-dark text-lighter" onClick={guardar}>Guardar Cambios</button>
            </div>
        </div>
    );
};

export default GeneralDemandParameters;
