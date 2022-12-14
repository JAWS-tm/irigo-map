import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Navigate, Outlet, useLocation } from 'react-router';
import { useAuth } from '../../hooks/auth';
import { useDispatch, useSelector } from 'react-redux';
import { setRequestedPage } from '../../store/slices/authSlice';
import Loader from '../Loader';

const PrivateRoute = () => {
  const auth = useAuth();
  const dispatch = useDispatch();
  const location = useLocation();

  const initialLoading = useSelector((state) => state.auth.initialLoad);

  useEffect(() => {
    if (!auth && location.pathname != '/logout') dispatch(setRequestedPage(location.pathname));
  }, [location, dispatch]);

  if (initialLoading) {
    return (
      <div
        style={{
          display: 'flex',
          width: '100%',
          height: '100%',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <Loader size={200} />
      </div>
    );
  }

  return auth ? <Outlet /> : <Navigate to="/sign-in" />;
};

PrivateRoute.propTypes = {};

export default PrivateRoute;
