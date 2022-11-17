import React from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import classNames from 'classnames';

const Button = ({ text, className, onClick }) => {
  return (
    <button className={classNames('primary-button', className)} onClick={onClick}>
      {text}
    </button>
  );
};
//used to make sure the data you receive is valid
//we need to do: name: PropTypes.type   type can be: func, array, string,
Button.propTypes = {
  text: PropTypes.string.isRequired,
  className: PropTypes.string,
  onClick: PropTypes.func,
};

export default Button;
