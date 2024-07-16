import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const CrearVenta = () => {
    const [articulos, setArticulos] = useState([{ idArticuloVenta: '', cantidadArticulo: '' }]);
    const [fechaVenta, setFechaVenta] = useState('');

    const handleArticuloChange = (index, event) => {
        const { name, value } = event.target;
        const list = [...articulos];
        list[index][name] = value;
        setArticulos(list);
    };

    const handleAddArticulo = () => {
        setArticulos([...articulos, { idArticuloVenta: '', cantidadArticulo: '' }]);
    };

    const handleRemoveArticulo = index => {
        const list = [...articulos];
        list.splice(index, 1);
        setArticulos(list);
    };

    const handleSubmit = async event => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/crear/venta', {
                articulos,
                fechaVenta
            });
            
            console.log('Respuesta del servidor:', response.data);
        } catch (error) {
            console.error('Error al crear la venta:' + error.response.data);
        }
    };

    return (
        <div className="container mt-4">
            <h2 className="mb-4">Crear Venta</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="fechaVenta" className="form-label">Fecha de Venta</label>
                    <input
                        type="date"
                        className="form-control"
                        id="fechaVenta"
                        value={fechaVenta}
                        onChange={e => setFechaVenta(e.target.value)}
                        required
                    />
                </div>

                {articulos.map((articulo, index) => (
                    <div key={index} className="border p-3 mb-3">
                        <h4>Artículo {index + 1}</h4>
                        <div className="mb-3">
                            <label htmlFor={`idArticuloVenta${index}`} className="form-label">ID de Artículo</label>
                            <input
                                type="number"
                                className="form-control"
                                id={`idArticuloVenta${index}`}
                                name="idArticuloVenta"
                                value={articulo.idArticuloVenta}
                                onChange={e => handleArticuloChange(index, e)}
                                required
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor={`cantidadArticulo${index}`} className="form-label">Cantidad</label>
                            <input
                                type="number"
                                className="form-control"
                                id={`cantidadArticulo${index}`}
                                name="cantidadArticulo"
                                value={articulo.cantidadArticulo}
                                onChange={e => handleArticuloChange(index, e)}
                                required
                            />
                        </div>
                        {index !== 0 && (
                            <button
                                type="button"
                                className="btn btn-danger me-2"
                                onClick={() => handleRemoveArticulo(index)}
                            >
                                Eliminar Artículo
                            </button>
                        )}
                    </div>
                ))}
<div>
                <button
                    type="button"
                    className="btn btn-primary mb-3 me-2"
                    onClick={handleAddArticulo}
                >
                    Agregar Artículo
                </button>
                </div>
                <button type="submit" className="btn btn-success">
                    Crear Venta
                </button>
            </form>
            <div className="mt-3">
        <Link to="/" className="btn btn-primary">Inicio</Link>
      </div>
      <Alert variant="success">
      <Alert.Heading>Hey, nice to see you</Alert.Heading>
      <p>
        Aww yeah, you successfully read this important alert message. This
        example text is going to run a bit longer so that you can see how
        spacing within an alert works with this kind of content.
      </p>
      <hr />
      <p className="mb-0">
        Whenever you need to, be sure to use margin utilities to keep things
        nice and tidy.
      </p>
    </Alert>
        </div>
    );
};

export default CrearVenta;
