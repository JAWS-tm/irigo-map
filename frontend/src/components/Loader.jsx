import React from 'react';
import PropTypes from 'prop-types';
import loaderLottie from '../assets/lotties/loader.json';
import Lottie from 'react-lottie';

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: loaderLottie,
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice',
  },
};

const Loader = ({ size = 100 }) => {
  return (
    <div>
      <Lottie options={defaultOptions} width={size} height={size} />
    </div>
  );
};

Loader.propTypes = {
  size: PropTypes.number,
};

export default Loader;
