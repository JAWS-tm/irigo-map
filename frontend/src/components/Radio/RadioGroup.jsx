import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const RadioGroup = (props) => {
  const { children, className } = props;
  return <div className={classNames('radio-group', className)}>{children}</div>;
};

RadioGroup.propTypes = {
  className: PropTypes.string,
  children: PropTypes.any,
};

export default RadioGroup;
