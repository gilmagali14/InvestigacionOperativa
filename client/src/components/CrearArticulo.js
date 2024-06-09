import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function CrearArticulo() {
    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const onSubmit = async (data) => {
        setLoading(true);
        setError(null);

        try {
            await axios.post('http://localhost:8080/create/articulo', data);
            navigate('/articulos');
        } catch (error) {
            setError(error.message);
        } finally {
            setLoading(false);
        }
    };

    fetchTiposArticulo();
  }, []);

  const handleChangeTipoArticulo = (event) => {
    setSelectedTipoArticulo(event.target.value);
  };

    return (
        <div style={{ backgroundColor: '#BFC9CA', minHeight: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <div className="container p-5 rounded" style={{ backgroundColor: 'white' }}>
            <h2 className="mt-3">Crear Artículo</h2>
            <form onSubmit={handleSubmit(onSubmit)} className="mt-3">
                <div className="mb-3">
                    <label className="form-label">Nombre:</label>
                    <input type="text" {...register('nombre', { required: true })} className={`form-control ${errors.nombre ? 'is-invalid' : ''}`} />
                    {errors.nombre && <div className="invalid-feedback">Este campo es requerido</div>}
                </div>
                <div className="mb-3">
                    <label className="form-label">Descripción:</label>
                    <textarea {...register('descripcion', { required: true })} className={`form-control ${errors.descripcion ? 'is-invalid' : ''}`}></textarea>
                    {errors.descripcion && <div className="invalid-feedback">Este campo es requerido</div>}
                </div>
                <button type="submit" className="btn btn-primary" disabled={loading}>Crear Artículo</button>
            </form>
            <div className="mt-3">
                <Link to="/" className="btn btn-primary">Inicio</Link>
            </div>
            {loading && <p className="mt-3">Enviando datos...</p>}
            {error && <p className="mt-3 text-danger">Error: {error}</p>}
        </div>

    </div>

);
}

export default CrearArticulo;
