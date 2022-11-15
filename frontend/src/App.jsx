import { Route, Routes } from 'react-router-dom';
import './assets/styles/App.css';
import About from './pages/About';
import Home from './pages/Home';
import Map from './pages/Map';
import User from './pages/User';

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/home" element={<Home />} />
        <Route path="users" element={<User />} />
        <Route path="about" element={<About />} />
        <Route path="map" element={<Map />} />
      </Routes>
    </div>
  );
}

export default App;
