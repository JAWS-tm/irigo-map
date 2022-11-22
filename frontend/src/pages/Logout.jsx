import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router';
import { logout } from '../store/slices/authSlice';

const Logout = (props) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(logout()).then(() => {
      navigate('/', { replace: true });
    });
  }, []);

  return (
    <p style={{ height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
      Déconnexion
    </p>
  );
};

Logout.propTypes = {};

export default Logout;
