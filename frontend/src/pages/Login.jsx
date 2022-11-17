import React from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Input from '../components/Input';
import TextArea from '../components/TextArea';
import Button from '../components/Button';
//import bcrypt from 'bcryptjs';

const Login = (props) => {
  return (
    <div className="Login">
      <UnderlinedTitle>Se connecter</UnderlinedTitle>
      <form action="#">
        <Input
          label="Email"
          className="input"
          type={'email'}
          value={val}
          onChange={(event) => {
            val = event.target.value;
          }}
          id="email"
          required
        />
        <Input
          label="Mot de passe"
          className="input"
          type={'password'}
          value={pw}
          onChange={(event) => {
            pw = event.target.value;
          }}
          id="password"
          required
        />

        <div className="btn-wrapper">
          <Button onClick={EvenClick()} text="Connexion" />
        </div>
      </form>
    </div>
  );
};

Login.propTypes = {};

let val = '';
let pw = '';
//const urlSignin= ;

//EvenClick() get email and password
function EvenClick() {
  return () => {
    console.log('connexion ma gueule ' + val + '   ' + pw);
    /*//chekUser
    fetch(urlSignin, {
      method: 'GET',
      headers: {
        Authentication: 'Bearer Token',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        email: val,
        password: pw,
      }),
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById('password').style.backgroundColor = '#ffffff';
        sessionStorage.setItem('accessToken', data.accessToken);
      })
      .catch((err) => {
        document.getElementById('password').value = '';
        document.getElementById('password').style.backgroundColor = '#ff0000';
      });*/
  };
}

export default Login;
