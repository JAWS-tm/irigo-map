import React from 'react';
import PropTypes from 'prop-types';

const RadioOption = ({ label, radioValue, field }) => {
  const id = label + '-radio';

  return (
    <div className="radio-input">
      <input
        type="radio"
        id={id}
        {...field}
        value={radioValue}
        checked={field.value === radioValue}
      />
      <label htmlFor={id}>{label}</label>
    </div>
  );
};

RadioOption.propTypes = {
  label: PropTypes.string.isRequired,
  radioValue: PropTypes.string.isRequired,
};

export default RadioOption;
