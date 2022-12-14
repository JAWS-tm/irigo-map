import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Navigate, Outlet, useLocation } from 'react-router';
import { useAuth } from '../../hooks/auth';
import { useDispatch, useSelector } from 'react-redux';
import { selectUserRole, setRequestedPage } from '../../store/slices/authSlice';
import { UserRoles } from '../../constants';

const RestrictedRoute = ({ role }) => {
  const userRole = useSelector(selectUserRole);

  // Check role (Admin have all access)
  const hasAccess = role === userRole || userRole == UserRoles.ADMIN;

  return hasAccess ? <Outlet /> : <Navigate to="/" />;
};

RestrictedRoute.propTypes = {
  role: PropTypes.oneOf(Object.values(UserRoles)).isRequired,
};

export default RestrictedRoute;
