import React from 'react';
import PropTypes from 'prop-types';

const UnderlinedTitle = ({ children }) => {
  return <h2 className="underlined-title">{children}</h2>;
};

UnderlinedTitle.propTypes = {};

export default UnderlinedTitle;
