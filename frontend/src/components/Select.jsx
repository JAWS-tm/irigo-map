import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import { useState } from 'react';
import { useRef } from 'react';

const Select = ({ value, onChange, options, className, label, ...props }) => {
  return (
    <div className={classNames('input-outlined', className)}>
      <select
        className={classNames('field', value === '' && 'empty')}
        {...props}
        value={value}
        onChange={(e) => onChange(e.target.value)}
      >
        <option value="" disabled>
          {/* Sélectionnez une option */}
        </option>
        {options &&
          options.map((option) => (
            <option value={option.value} key={option.value}>
              {option.label}
            </option>
          ))}
      </select>
      {/* <input className="field" type={type} placeholder=" " ref={ref} {...props} /> */}
      <span className="label">{label}</span>
    </div>
  );
};

Select.propTypes = {};

export default Select;
