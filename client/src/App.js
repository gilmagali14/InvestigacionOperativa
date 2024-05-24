import { Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Articulos from './components/Articulos';
import CrearArticulo from './components/CrearArticulo';

const App = () => {
 return (
    <>
       <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/crearArticulo" element={<CrearArticulo />} />
          <Route path="/articulos" element={<Articulos />} />
       </Routes>
    </>
 );
};

export default App;