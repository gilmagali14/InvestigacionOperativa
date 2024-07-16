import { Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Articulos from './components/Articulos';
import CrearArticulo from './components/CrearArticulo';
import CrearOrdenCompra from './components/CrearOrdenCompra';
import Demanda from './components/Demanda';
import VentasComponent from './components/VentasComponent';
import Inventario from './components/Inventario';
import Actualizar from './components/ActualizarArticulo';
import DemandasComponent from './components/Demandas';
import OrdenCompra from './components/OrdenDeCompra';
import CrearVenta from './components/CrearVenta';

const App = () => {
 return (
    <>
       <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/crear/articulo" element={<CrearArticulo />} />
          <Route path="/articulos" element={<Articulos />} />
          <Route path="/crear/orden-de-compra/:demandaCalculada" element={<CrearOrdenCompra />} />
          <Route path="/inventario" element={<Inventario />} />
          <Route path="/demanda" element={<Demanda />} />
          <Route path="/actualizar/articulo" element={<Actualizar />} />
          <Route path="/demandas" element={<DemandasComponent />} />
          <Route path="/ordenes-de-compra" element={<OrdenCompra />} />
          <Route path="/crear/venta" element={<CrearVenta />} />

          <Route path="/ventas" element={<VentasComponent />} />
       </Routes>
    </>
 );
};

export default App;