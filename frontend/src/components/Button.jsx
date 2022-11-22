import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const Button = ({ text, className, onClick, type, disabled }) => {
  return (
    <button
      className={classNames('primary-button', className)}
      type={type}
      onClick={onClick}
      disabled={disabled}
    >
      {text}
    </button>
  );
};

Button.defaultProps = {
  type: 'button',
};
//used to make sure the data you receive is valid
//we need to do: name: PropTypes.type   type can be: func, array, string,
Button.propTypes = {
  text: PropTypes.string.isRequired,
  className: PropTypes.string,
  onClick: PropTypes.func,
  type: PropTypes.oneOf(['submit', 'button']),
  disabled: PropTypes.bool,
};

export default Button;
