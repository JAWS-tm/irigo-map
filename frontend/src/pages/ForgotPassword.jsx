import React, { useState } from 'react';
import PropTypes from 'prop-types';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';
import userService from '../services/user.service';
import * as Yup from 'yup';
import { emailValidator } from '../helpers/validators';
import ErrorBanner from '../components/ErrorBanner';
import { useNavigate } from 'react-router-dom';
import SuccessMessage from '../components/SuccessMessage';

const ForgotPassword = (props) => {
  const [error, setError] = useState();
  const [sended, setSended] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleSubmit = (values) => {
    if (loading) return;
    setLoading(true);
    userService
      .forgotPassword(values.email)
      .then(() => {
        setSended(true);
      })
      .catch((e) => {
        setError(e.response.data?.errors.message ?? 'Une erreur est survenue, veuillez réessayer');
      })
      .finally(() => {
        setLoading(false);
      });
  };

  return (
    <div className="ForgotPassword page-wrapper">
      <UnderlinedTitle>Mot de passe oublié</UnderlinedTitle>

      {sended ? (
        <SuccessMessage
          message="Le mail de réinitialisation a été envoyé."
          buttonText="Retour à l'accueil"
          buttonClick={() => navigate('/')}
        />
      ) : (
        <Formik
          onSubmit={handleSubmit}
          validationSchema={Yup.object({
            email: emailValidator,
          })}
          initialValues={{ email: '' }}
        >
          <Form className="form-wrapper">
            <p className="info">
              Entrez votre adresse e-mail et nous vous enverrons un lien pour récupérer votre
              compte.
            </p>
            {error && <ErrorBanner message={error} />}

            <div className="field-wrapper">
              <Field name="email" label="Email" component={FormInput} />
              <Button type="submit" text="Envoyer un lien de connexion" loading={loading} />
            </div>
          </Form>
        </Formik>
      )}
    </div>
  );
};

ForgotPassword.propTypes = {};

export default ForgotPassword;
