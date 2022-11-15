import classNames from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';

const Input = ({ label, className, type, inputProps }) => {
  return (
    <div className={classNames('input-outlined', className)}>
      <input className="field" type={type} placeholder=" " {...inputProps} />
      <span className="label">{label}</span>
    </div>
  );
};

Input.defaultProps = {
  type: 'text',
};

Input.propTypes = {
  className: PropTypes.string,
  type: PropTypes.oneOf(['password', 'email', 'text', 'number']),
  inputProps: PropTypes.object,
};

export default Input;
