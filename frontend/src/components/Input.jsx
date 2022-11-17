import classNames from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';

const Input = ({ label, className, type, inputProps, value, onChange, id }) => {
  return (
    <div className={classNames('input-outlined', className)}>
      <input
        className="field"
        type={type}
        placeholder=" "
        {...inputProps}
        value={value}
        onChange={onChange}
        id=""
      />
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
  value: PropTypes.any,
  onChange: PropTypes.func,
  id: PropTypes.string,
};

export default Input;
