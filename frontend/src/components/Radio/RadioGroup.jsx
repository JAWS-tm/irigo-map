import React from 'react';
import PropTypes from 'prop-types';
import RadioOption from './RadioOption';
import classNames from 'classnames';

const RadioGroup = (props) => {
  return (
    <div className={classNames('radio-group', props.className)}>
      {React.Children.map(props.children, (child) => {
        if (child.type === RadioOption)
          return React.cloneElement(child, {
            isChecked: props.value === child.props.value,
            name: props.name,
            onChange: props.onChange,
          });
        return child;
      })}
    </div>
  );
};

RadioGroup.propTypes = {
  className: PropTypes.string,
  name: PropTypes.string.isRequired,
  onChange: PropTypes.func.isRequired,
  value: PropTypes.string,
  children: PropTypes.any,
};

export default RadioGroup;
