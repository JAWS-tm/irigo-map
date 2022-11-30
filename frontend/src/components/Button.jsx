import React from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import Lottie from 'react-lottie';
import loadingData from '../assets/lotties/loading';

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: loadingData,
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice',
  },
};
const Button = ({ text, className, onClick, type, disabled, loading }) => {
  return (
    <button
      className={classNames('primary-button', className, loading && 'loading')}
      type={type}
      onClick={loading ? null : onClick}
      disabled={disabled}
    >
      {loading ? (
        <Lottie options={defaultOptions} width={20} height={20} />
      ) : (
        <span className="text">{text}</span>
      )}
    </button>
  );
};

Button.defaultProps = {
  type: 'button',
  loading: false,
};
//used to make sure the data you receive is valid
//we need to do: name: PropTypes.type   type can be: func, array, string,
Button.propTypes = {
  text: PropTypes.string.isRequired,
  className: PropTypes.string,
  onClick: PropTypes.func,
  type: PropTypes.oneOf(['submit', 'button']),
  disabled: PropTypes.bool,
  loading: PropTypes.bool,
};

export default Button;
