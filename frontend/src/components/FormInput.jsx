import classNames from 'classnames';
import React from 'react';
import Input from './Input';

const FormInput = ({
  className,
  field, // { name, value, onChange, onBlur }
  form: { touched, errors }, // also values, setXXXX, handleXXXX, dirty, isValid, status, etc.
  ...props
}) => (
  <div className={classNames('form-input', className)}>
    <Input type="text" {...field} {...props} />
    {touched[field.name] && errors[field.name] && <div className="error">{errors[field.name]}</div>}
  </div>
);

export default FormInput;
