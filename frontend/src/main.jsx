import React from 'react';
import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import './assets/styles/index.scss';
import 'leaflet/dist/leaflet.css';
import 'react-loading-skeleton/dist/skeleton.css';
import moment from 'moment';
import 'moment/dist/locale/fr';
import store from './store/store';
moment.locale('fr'); // configure moment in french

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Provider store={store}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </Provider>
  </React.StrictMode>
);
