import React from 'react';
import PropTypes from 'prop-types';
import { Navigate, Outlet, Route } from 'react-router';
import { useAuth } from '../../hooks/auth';

const PublicRoute = ({ element, ...rest }) => {
  const auth = useAuth();

  return auth ? <Navigate to="/" /> : <Outlet />;
};

PublicRoute.propTypes = {};

export default PublicRoute;
