import axios from "axios";
import { useEffect, useState } from "react";

export default function ModalBestOption({ setShow, historicalDemand }) {
  const [parameterBestMetod, setParameterBestMetod] = useState({
    alfa: 0.2,
    initialValue: 0,
    periods: 3,
    ponderation: Array.from({ length: 3 }, (_, i) => i + 1),
    errorMetod: "MAD",
  });
  const [disabled, setDisabled] = useState(false);
  const [modal, setModal] = useState(true);
  const [method, setMethod] = useState("");

  useEffect(() => {
    validate();
  }, [parameterBestMetod]);

  const validate = () => {
    const { alfa, initialValue, periods, ponderation, errorMetod } = parameterBestMetod;
    if (alfa && initialValue != null && periods && ponderation.length && errorMetod && ponderation.length === periods) {
      setDisabled(false);
    } else {
      setDisabled(true);
    }
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "ponderation") {
      const ponderation = value.split(",").map(val => val === "" ? 0 : parseFloat(val));
      setParameterBestMetod(prev => ({ ...prev, [name]: ponderation }));
    } else {
      setParameterBestMetod(prev => ({ ...prev, [name]: value }));
    }
  }

  const handleBestMetodChange = async (e) => {
    e.preventDefault();
    try {
      const body = {
        demandaHistorica: historicalDemand,
        alfa: parameterBestMetod.alfa,
        valorInicial: parameterBestMetod.initialValue,
        errorMetod: parameterBestMetod.errorMetod,
        cantidadPeriodos: {
          cantidadPeriodo: parameterBestMetod.periods,
          peso: parameterBestMetod.ponderation
        }
      };
      console.log(body);
      const response = await axios.post("http://localhost:8080/mejor-metodo", body);
      setMethod(response.data);
    } catch (error) {
      console.log(error);
    }
  }

  const handleCloseModal = () => {
    setShow(false); 
    setModal(false); 
  }

  return (
    <>
      <div className={`modal fade ${modal ? 'show' : ''}`} tabIndex="-1" role="dialog" style={{ display: modal ? 'block' : 'none' }}>
        <div className="modal-dialog" role="document">
          <div className="modal-content">
            <div className="modal-header">
                <div className="text-center">
              <h5 className="modal-title text-center">Configuración del Mejor Método</h5>
              </div>
              <button type="button" className="btn-close" aria-label="Close" onClick={handleCloseModal}></button>
            </div>
            <div className="modal-body">
            {method.trim() == "" ? (
                <form onSubmit={handleBestMetodChange}>
                  <div className="mb-3">
                    <label htmlFor="alfa" className="form-label">Alfa</label>
                    <input
                      type="number"
                      id="alfa"
                      name="alfa"
                      value={parameterBestMetod.alfa}
                      onChange={handleChange}
                      className="form-control"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="initialValue" className="form-label">Valor Inicial</label>
                    <input
                      type="number"
                      id="initialValue"
                      name="initialValue"
                      value={parameterBestMetod.initialValue}
                      onChange={handleChange}
                      className="form-control"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="periods" className="form-label">Periodos Atras</label>
                    <input
                      type="number"
                      id="periods"
                      name="periods"
                      value={parameterBestMetod.periods}
                      onChange={handleChange}
                      className="form-control"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="ponderation" className="form-label">Ponderación</label>
                    <input
                      type="text"
                      id="ponderation"
                      name="ponderation"
                      value={parameterBestMetod.ponderation ? parameterBestMetod.ponderation.toString() : ""}
                      onChange={handleChange}
                      className="form-control"
                    />
                  </div>
                  <div className="mb-3">
                    <label htmlFor="errorMetod" className="form-label">Error</label>
                    <select
                      id="errorMetod"
                      name="errorMetod"
                      value={parameterBestMetod.errorMetod}
                      onChange={handleChange}
                      className="form-select"
                    >
                      <option value="MAD">MAD</option>
                      <option value="MSE">MSE</option>
                      <option value="MAPE">MAPE</option>
                    </select>
                  </div>
                  <button
                    type="submit"
                    className={`btn btn-primary ${disabled ? "disabled" : ""}`}
                    disabled={disabled}
                  >
                    Guardar
                  </button>
                </form>
              ) : (
                <div className="modal-body d-flex justify-content-center" style={{backgroundColor: '#77B8A8'}}>
                    <div>
                <h5>Mejor metodo para predecir la demanda: </h5>
                <div className="d-flex justify-context-center">
                    <h5><b>{method}</b></h5>
                    </div>

                    </div>
                </div>
              )}
            </div>
            <div className="modal-footer">
              <button type="button" className="btn btn-secondary" onClick={handleCloseModal}>Close</button>
            </div>
          </div>
        </div>
      </div>
      {modal && <div className="modal-backdrop fade show" onClick={handleCloseModal}></div>}
    </>
  )
}
