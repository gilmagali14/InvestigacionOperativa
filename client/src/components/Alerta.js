import React from 'react';

const Alerta = ({ type, message }) => {
    return (
        <div className={`alert alert-${type}`} role="alert">
            {message}
        </div>
    );
};

export default Alerta;
