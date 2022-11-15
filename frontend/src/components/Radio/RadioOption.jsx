import React from 'react';
import PropTypes from 'prop-types';

const RadioOption = ({ label, name, onChange, isChecked, value }) => {
  const id = value + '-radio';
  return (
    <div className="radio-input">
      <input
        type="radio"
        name={name}
        onChange={onChange}
        value={value}
        id={id}
        checked={isChecked}
      />
      <label htmlFor={id}>{label}</label>
    </div>
  );
};

RadioOption.propTypes = {
  label: PropTypes.string.isRequired,
  value: PropTypes.string.isRequired,
  name: PropTypes.string,
  onChange: PropTypes.func,
  isChecked: PropTypes.bool,
};

export default RadioOption;
