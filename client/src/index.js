import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import { ToastContainer } from 'react-toastify';

const root = ReactDOM.createRoot(document.getElementById('root'));


root.render(
 <React.StrictMode>
    <BrowserRouter>
    <ToastContainer />
   <App />   
   </BrowserRouter>
 </React.StrictMode>
);