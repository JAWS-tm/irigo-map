import React, { useEffect } from 'react';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Input from '../components/Input';
import Button from '../components/Button';
import { useDispatch, useSelector } from 'react-redux';
import { clearAuthError, login, selectAuthError } from '../store/slices/authSlice';
import * as Yup from 'yup';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';
import ErrorBanner from '../components/ErrorBanner';
import axios from 'axios';
import { API_URL } from '../config/config';
import authHeader from '../services/auth-header';

const initialValues = {
  email: '',
  password: '',
};

const validationSchema = Yup.object({
  email: Yup.string().email('Adresse e-mail invalide').required('Requis'),
  password: Yup.string()
    .required('Requis')
    .min(8, 'Votre mot de passe doit comporter plus de 8 caractères')
    .max(60),
});

const Login = (props) => {
  const dispatch = useDispatch();
  const handleSubmit = (values, { setSubmitting }) => {
    dispatch(login(values));
  };

  useEffect(() => {
    return () => dispatch(clearAuthError());
  }, []);

  const authError = useSelector(selectAuthError);

  return (
    <div className="Login">
      <UnderlinedTitle>Se connecter</UnderlinedTitle>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form>
          {authError && <ErrorBanner message={authError} className="request-error" />}

          <Field name="email" label="Email" className="input" component={FormInput} />
          <Field
            name="password"
            label="Mot de passe"
            type="password"
            className="input"
            component={FormInput}
          />

          <div className="btn-wrapper">
            <Button type="submit" text="Connexion" />
          </div>
        </Form>
      </Formik>
    </div>
  );
};

Login.propTypes = {};

export default Login;
