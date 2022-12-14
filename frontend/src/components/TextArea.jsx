import classNames from 'classnames';
import { useField } from 'formik';
import React from 'react';

const TextArea = ({ label, className, fieldName, ...props }) => {
  const [field, meta, helpers] = useField(props);
  return (
    <div className={classNames('form-input', className)}>
      <div className={classNames('input-outlined', className)}>
        <textarea
          className="field"
          type="text"
          placeholder=" "
          value={field.value}
          onChange={field.onChange}
          {...props}
        />
        <span className="label">{label}</span>
      </div>
      {meta.touched && meta.error && <div className="error">{meta.error}</div>}
    </div>
  );
};

TextArea.propTypes = {};

export default TextArea;
