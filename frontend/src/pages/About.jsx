import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import UnderlinedTitle from '../components/UnderlinedTitle';

const About = (props) => {
  return (
    <div className="About">
      <UnderlinedTitle className="title">A propos</UnderlinedTitle>
      <div className="card-section">
        <div className="card-list">
          <div className="card">
            <i className="fa-solid"></i>
            <Link to="/home">Accueil</Link>
          </div>
          <div className="card">
            <i className="fa-solid"></i>
            <Link to="/map">Map</Link>
          </div>
        </div>
      </div>
    </div>
  );
};

About.propTypes = {};

export default About;
