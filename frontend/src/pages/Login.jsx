import React, { useEffect } from 'react';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import { useDispatch, useSelector } from 'react-redux';
import {
  clearAuthError,
  login,
  selectAuthError,
  selectAuthIsLoading,
  selectRequestedPage,
} from '../store/slices/authSlice';
import * as Yup from 'yup';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';
import ErrorBanner from '../components/ErrorBanner';
import StyledLink from '../components/Link';
import { emailValidator } from '../helpers/validators';
import { useNavigate } from 'react-router-dom';

const initialValues = {
  email: '',
  password: '',
};

const validationSchema = Yup.object({
  email: emailValidator,
  password: Yup.string()
    .required('Requis')
    .min(8, 'Votre mot de passe doit comporter plus de 8 caractères')
    .max(60),
});

const Login = (props) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const requestedPage = useSelector(selectRequestedPage);

  const authError = useSelector(selectAuthError);
  const loading = useSelector(selectAuthIsLoading);

  const handleSubmit = (values, { setSubmitting }) => {
    if (loading) return;

    dispatch(login(values)).then((res) => {
      if (res.type === 'auth/login/fulfilled') navigate(requestedPage ?? '/');
    });
  };

  useEffect(() => {
    return () => dispatch(clearAuthError());
  }, []);

  return (
    <div className="Login page-wrapper">
      <UnderlinedTitle>Se connecter</UnderlinedTitle>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        {({ isValid, dirty }) => (
          <Form className="form-wrapper">
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
              <Button
                type="submit"
                text="Connexion"
                loading={loading}
                disabled={!(isValid && dirty)}
              />
              <StyledLink
                text="Mot de passe oublié ?"
                to="/forgot-password"
                className="forgot-password"
              />
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

Login.propTypes = {};

export default Login;
