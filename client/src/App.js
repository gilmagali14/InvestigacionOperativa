import { Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Articulos from './components/Articulos';
import CrearArticulo from './components/CrearArticulo';
import CrearOrdenCompra from './components/CrearOrdenCompra';
import Inventario from './components/Inventario';

const App = () => {
 return (
    <>
       <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/crear/articulo" element={<CrearArticulo />} />
          <Route path="/articulos" element={<Articulos />} />
          <Route path="/crear/orden-de-compra" element={<CrearOrdenCompra />} />
          <Route path="/inventario" element={<Inventario />} />

       </Routes>
    </>
 );
};

export default App;