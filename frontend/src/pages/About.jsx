import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';
import { useSelector } from 'react-redux';
import { selectCurrentUser } from '../store/slices/authSlice';
import { UserRoles } from '../constants';

const About = (props) => {
  const role = useSelector(selectCurrentUser).role;
  return (
    <div className="About">
      <UnderlinedTitle className="title">Outils</UnderlinedTitle>
      <div className="card-section">
        <div className="card-list">
          <div className="card">
            <Link to="/profile">Informations Utilisateur</Link>
          </div>
          <div className="card">
            <Link
              to={role === UserRoles.USER ? '/request-grade' : null}
              onClick={
                role !== UserRoles.USER
                  ? () => alert('Vous avez déjà accès à cette fonctionnalité')
                  : null
              }
            >
              Demande Data Scientist
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

About.propTypes = {};

export default About;
