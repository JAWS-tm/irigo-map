import { Route, Routes } from 'react-router-dom';
import './assets/styles/App.css';
import About from './pages/About';
import Home from './pages/Home';

function App() {
  return (
    <div className="App">
      <h1>Welcome to React Router!</h1>
      <Routes>
        <Route path="/" element={<Home />}>
          <Route path="users" element={null} />
        </Route>
        <Route path="about" element={<About />} />
      </Routes>
    </div>
  );
}

export default App;
