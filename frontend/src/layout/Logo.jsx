import React from 'react';
import classNames from 'classnames';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

const Logo = ({ className }) => {
  return (
    <Link to={'/'}>
      <h1 className={classNames('app-name', className)}>
        IrigoMap
        <i className="fa-solid fa-location-dot"></i>
      </h1>
    </Link>
  );
};

Logo.propTypes = {
  className: PropTypes.string,
};

export default Logo;
