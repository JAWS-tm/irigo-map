import React from 'react';
import PropTypes from 'prop-types';
import Button from './Button';

const SuccessMessage = ({ message, buttonText, buttonClick, showButton }) => {
  return (
    <>
      <div className="SuccessMessage">
        <i className="fa-solid fa-circle-check"></i>
        {message}
      </div>
      {showButton && <Button onClick={buttonClick} text={buttonText}></Button>}
    </>
  );
};

SuccessMessage.defaultProps = {
  showButton: true,
};

SuccessMessage.propTypes = {
  message: PropTypes.string.isRequired,
  buttonText: PropTypes.string.isRequired,
  buttonClick: PropTypes.func.isRequired,
  showButton: PropTypes.bool,
};

export default SuccessMessage;
