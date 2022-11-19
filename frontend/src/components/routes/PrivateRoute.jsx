import React from 'react';
import PropTypes from 'prop-types';
import { Navigate, Outlet, Route } from 'react-router';
import { useAuth } from '../../hooks/auth';

const PrivateRoute = ({ element, ...rest }) => {
  const auth = useAuth();

  return auth ? <Outlet /> : <Navigate to="/sign-in" />;
};

PrivateRoute.propTypes = {};

export default PrivateRoute;
