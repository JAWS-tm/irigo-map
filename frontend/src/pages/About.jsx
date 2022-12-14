import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';
import PopupError from '../components/PopupError';

const About = (props) => {
  return (
    <div className="About">
      <UnderlinedTitle className="title">Outils</UnderlinedTitle>
      <div className="card-section">
        <div className="card-list">
          <div className="card">
            <Link to="/user_data">Informations Utilisateur</Link>
          </div>
          <div className="card">
            <Link onClick={PopupError}>Demande Data Scientist</Link>
          </div>
          <div className="card">
            <Link onClick={PopupError}>Demande Administrateur</Link>
          </div>
          <div className="card">
            <Link to="/Comments">Commentaires Lignes</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

About.propTypes = {};

export default About;
