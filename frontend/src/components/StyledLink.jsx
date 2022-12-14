import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import classNames from 'classnames';

const StyledLink = ({ to, text, className }) => {
  return (
    <Link to={to} className={classNames('StyledLink', className)}>
      {text}
    </Link>
  );
};

StyledLink.propTypes = {
  to: PropTypes.string.isRequired,
  text: PropTypes.string.isRequired,
};

export default StyledLink;
