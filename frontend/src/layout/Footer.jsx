import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import Logo from './Logo';

const Footer = (props) => {
  return (
    <div className="Footer">
      <Logo className="app-logo" />
      <div className="useful-links">
        <Link to="/">Accueil</Link>
        <Link to="/contact">Contact</Link>
        <Link to={''}>CGU</Link>
        <Link to={''}>Politique de confidentialité</Link>
      </div>
      <p className="copyright">
        © Copyright <span>IrigoMap</span>. All Rights Reserved
      </p>
    </div>
  );
};

Footer.propTypes = {};

export default Footer;
