import React from 'react';
import PropTypes from 'prop-types';
import Lottie from 'react-lottie';
import notFoundLottie from '../assets/lotties/not-found.json';
import Button from '../components/Button';
import { useNavigate } from 'react-router-dom';

const defaultOptions = {
  loop: true,
  autoplay: true,
  animationData: notFoundLottie,
  rendererSettings: {
    preserveAspectRatio: 'xMidYMid slice',
  },
};
const NotFound = (props) => {
  const navigate = useNavigate();
  return (
    <div className="NotFound">
      <Lottie
        options={defaultOptions}
        style={{ maxWidth: '90%', width: '400px', aspectRatio: '3/2' }}
      />
      <h2>La page demandée n'existe pas</h2>
      <Button
        text="Retour à l'accueil"
        onClick={() => {
          navigate('/');
        }}
      />
    </div>
  );
};

NotFound.propTypes = {};

export default NotFound;
