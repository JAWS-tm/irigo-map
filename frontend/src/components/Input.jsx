import classNames from 'classnames';
import React from 'react';
import PropTypes from 'prop-types';

const Input = React.forwardRef(({ label, className, type, ...props }, ref) => (
  <div className={classNames('input-outlined', className)}>
    <input className="field" type={type} placeholder=" " ref={ref} {...props} />
    <span className="label">{label}</span>
  </div>
));
Input.displayName = 'Input';

Input.defaultProps = {
  type: 'text',
};

Input.propTypes = {
  label: PropTypes.string,
  className: PropTypes.string,
  type: PropTypes.oneOf(['password', 'email', 'text', 'number']),
};

export default Input;
