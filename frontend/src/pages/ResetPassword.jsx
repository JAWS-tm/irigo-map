import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';
import { confirmPasswordValidator, passwordValidator } from '../helpers/validators';
import * as Yup from 'yup';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import userService from '../services/user.service';
import ErrorBanner from '../components/ErrorBanner';
import SuccessMessage from '../components/SuccessMessage';

const validationSchema = Yup.object({
  password: passwordValidator,
  confirmPassword: confirmPasswordValidator,
});

const ResetPassword = (props) => {
  const [error, setError] = useState();
  const [tokenError, setTokenError] = useState();
  const [passswordChanged, setPassswordChanged] = useState();
  const [loading, setLoading] = useState(false);
  const params = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!params.token) navigate('/');
    else
      userService.validatePasswordToken(params.token).catch((e) => {
        setTokenError(e.response.data?.errors.message);
      });
  }, []);

  const handleSubmit = (values) => {
    if (loading) return;
    setLoading(true);
    userService
      .resetPassword(params.token, values.password)
      .then((res) => {
        setPassswordChanged(true);
      })
      .catch((e) => {
        setError(e.response.data?.errors.message ?? 'Une erreur est survenue, veuillez réessayer');
      })
      .finally(() => {
        setLoading(false);
      });
  };
  return (
    <div className="ResetPassword page-wrapper">
      <UnderlinedTitle>Réinitialisation du mot de passe</UnderlinedTitle>
      {tokenError ? (
        <>
          <div className="token-error">
            <i className="fa-solid fa-circle-exclamation"></i>
            {tokenError}
          </div>
          <Button onClick={() => navigate('/')} text="Retour à l'accueil"></Button>
        </>
      ) : passswordChanged ? (
        <SuccessMessage
          message="Votre mot de passe à bien été modifié. Vous pouvez vous connecter"
          buttonText="Connexion"
          buttonClick={() => navigate('/sign-in')}
        />
      ) : (
        <Formik
          onSubmit={handleSubmit}
          validationSchema={validationSchema}
          initialValues={{ password: '', confirmPassword: '' }}
        >
          <Form className="form-wrapper">
            {error ? (
              <ErrorBanner message={error} />
            ) : (
              <>
                <p className="info">
                  Saisissez un nouveau mot de passe, incluant 1 majuscule, 1 minuscule, 1 nombre et
                  un caractère spécial.
                </p>
                <div className="field-wrapper">
                  <Field
                    name="password"
                    label="Mot de passe"
                    type="password"
                    component={FormInput}
                  />
                  <Field
                    name="confirmPassword"
                    label="Confirmez le mot de passe"
                    type="password"
                    component={FormInput}
                  />
                  <Button type="submit" text="Réinitialiser" loading={loading} />
                </div>
              </>
            )}
          </Form>
        </Formik>
      )}
    </div>
  );
};

ResetPassword.propTypes = {};

export default ResetPassword;
