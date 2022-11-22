import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

const ErrorBanner = ({ message, className }) => {
  return (
    <div className={classNames('error-message', className)}>
      <i className="fa-regular fa-circle-xmark"></i>
      <p>{message}</p>
    </div>
  );
};

ErrorBanner.propTypes = {
  className: PropTypes.string,
  message: PropTypes.string.isRequired,
};

export default ErrorBanner;
