// src/App.js
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './components/Home';
import Articulos from './components/Articulos';
import CrearArticulo from './components/CrearArticulo';
import VentasComponent from './components/VentasComponent';
import Demanda from './components/Demanda';
import Actualizar from './components/ActualizarArticulo';
import OrdenCompra from './components/OrdenDeCompra';
import CrearVenta from './components/CrearVenta';
import InventarioAll from './components/InventarioAll';
import Footer from './components/Footer';

const App = () => {
  return (
    <div className='bg-light' style={{
      display: 'flex',
      flexDirection: 'column'
    }}>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/crear/articulo" element={<CrearArticulo />} />
        <Route path="/articulos" element={<Articulos />} />
        <Route path="/inventario" element={<InventarioAll />} />
        <Route path="/demanda" element={<Demanda />} />
        <Route path="/actualizar/articulo" element={<Actualizar />} />
        <Route path="/ordenes-de-compra" element={<OrdenCompra />} />
        <Route path="/crear/venta" element={<CrearVenta />} />
        <Route path="/ventas" element={<VentasComponent />} />
      </Routes>
      <Footer />
    </div>
  );
};

export default App;
