import { Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Articulos from './components/Articulos';
import CrearArticulo from './components/CrearArticulo';
import CrearOrdenCompra from './components/CrearOrdenCompra';
import Demanda from './components/Demanda';
import VentasComponent from './components/VentasComponent';
import Inventario from './components/Inventario';
import Inventarios from './components/Inventarios';
import Actualizar from './components/ActualizarArticulo';

const App = () => {
 return (
    <>
       <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/crear/articulo" element={<CrearArticulo />} />
          <Route path="/articulos" element={<Articulos />} />
          <Route path="/crear/orden-de-compra" element={<CrearOrdenCompra />} />
          <Route path="/inventario" element={<Inventario />} />
          <Route path="/inventarios" element={<Inventarios />} />
          <Route path="/demanda" element={<Demanda />} />
          <Route path="/ventas/:idArticulo/:demandaCalcualda" element={<VentasComponent />} />
          <Route path="/actualizar/articulo" element={<Actualizar />} />
       </Routes>
    </>
 );
};

export default App;