import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import About from './pages/About';
import Home from './pages/Home';
import Navbar from './layout/Navbar';
import Footer from './layout/Footer';
import Contact from './pages/Contact';
import Help from './pages/Help';
import ContentLayout from './layout/ContentLayout';
import Login from './pages/Login';
import Register from './pages/Register';
import Map from './pages/Map';
import User_data from './pages/User_data';
import PrivateRoute from './components/routes/PrivateRoute';
import PublicRoute from './components/routes/PublicRoute';
import Logout from './pages/Logout';
import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getMe, selectRequestedPage } from './store/slices/authSlice';
import ForgotPassword from './pages/ForgotPassword';
import ResetPassword from './pages/ResetPassword';
import Comments from './pages/Comments';

function App() {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const requestedPage = useSelector(selectRequestedPage);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token && token.length > 1)
      dispatch(getMe()).then(() => requestedPage && navigate(requestedPage));
  }, [requestedPage]);

  return (
    <>
      <Navbar />
      <Routes>
        <Route path="/" element={<ContentLayout />}>
          <Route index element={<Home />} />
          <Route path="about" element={<About />} />
          <Route path="contact" element={<Contact />} />
          <Route path="help" element={<Help />} />
          <Route path="comments" element={<Comments />} />

          <Route path="/" element={<PrivateRoute />}>
            <Route path="map" element={<Map />} />
            <Route path="logout" element={<Logout />} />
            <Route path="user_data" element={<User_data />} />
          </Route>
          <Route path="/" element={<PublicRoute />}>
            <Route path="sign-in" element={<Login />} />
            <Route path="sign-up" element={<Register />} />
            <Route path="forgot-password" element={<ForgotPassword />} />
            <Route path="reset-password/:token" element={<ResetPassword />} />
          </Route>
        </Route>
      </Routes>
      <Footer />
    </>
  );
}

export default App;
