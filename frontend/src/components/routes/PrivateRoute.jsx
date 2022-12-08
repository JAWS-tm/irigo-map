import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Navigate, Outlet, useLocation } from 'react-router';
import { useAuth } from '../../hooks/auth';
import { useDispatch } from 'react-redux';
import { setRequestedPage } from '../../store/slices/authSlice';

const PrivateRoute = () => {
  const auth = useAuth();
  const dispatch = useDispatch();
  const location = useLocation();

  useEffect(() => {
    if (!auth && location.pathname != '/logout') dispatch(setRequestedPage(location.pathname));
  }, [location, dispatch]);

  return auth ? <Outlet /> : <Navigate to="/sign-in" />;
};

PrivateRoute.propTypes = {};

export default PrivateRoute;
