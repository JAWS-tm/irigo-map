import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Input from '../components/Input';
import TextArea from '../components/TextArea';
import Button from '../components/Button';

const Login = (props) => {
  return (
    <div className="Login">
      <UnderlinedTitle>Se connecter</UnderlinedTitle>
      <form action="#">
        <Input label="Email" className="input" type={'email'} />
        <Input label="Mot de passe" className="input" type={'password'} />

        <div className="btn-wrapper">
          <Button text="Connexion" />
        </div>
      </form>
    </div>
  );
};

Login.propTypes = {};

export default Login;
