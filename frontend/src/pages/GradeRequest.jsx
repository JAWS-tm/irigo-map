import React from 'react';
import PropTypes from 'prop-types';
import Input from '../components/Input';
import TextArea from '../components/TextArea';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import PopupError from '../components/PopupError';
import { Field, Form, Formik } from 'formik';
import * as Yup from 'yup';
import userService from '../services/user.service';
import { useState } from 'react';
import FormInput from '../components/FormInput';
import ErrorBanner from '../components/ErrorBanner';
import { Navigate, useNavigate } from 'react-router-dom';
import SuccessMessage from '../components/SuccessMessage';
import { useEffect } from 'react';

const initialValues = {
  job: '',
  company: '',
  description: '',
};

const validationSchema = Yup.object({
  job: Yup.string().max(30, 'Trop de caractères').required('Requis'),
  company: Yup.string().max(30, 'Trop de caractères').required('Requis'),
  description: Yup.string().max(500, 'Trop de caractères').required('Requis'),
});

const GradeRequest = (props) => {
  const [loading, setLoading] = useState(false);
  const [sended, setSended] = useState(false);
  const [alreadyDone, setAlreadyDone] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (values) => {
    setLoading(true);
    userService
      .requestGrade(values.job, values.company, values.description)
      .then((res) => {
        if (res.data.status === 'OK') setSended(true);
      })
      .catch((err) => {
        setError(err.response.data?.errors.message);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  useEffect(() => {
    userService.hasDoneGradeRequest().then((res) => {
      if (res.data.status === 'OK') setAlreadyDone(true);
    });
  }, []);

  return (
    <div className="GradeRequest">
      <UnderlinedTitle>Demande de grade</UnderlinedTitle>

      {sended || alreadyDone ? (
        <SuccessMessage
          message={
            sended
              ? 'Votre demande à bien été prise en compte, nous allons la traiter au plus vite.'
              : 'Vous avez déjà effectué la demande, son traitement est en cours.'
          }
          buttonText="Retour au compte"
          buttonClick={() => navigate('/profile')}
        />
      ) : (
        <>
          <p style={{ padding: '0 50px', textAlign: 'center' }}>
            Remplissez ce formulaire pour demander le role de Data Scientist. Votre demande sera
            traitée dès que possible.
          </p>
          <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={handleSubmit}
          >
            {({ isValid, dirty }) => (
              <Form className="form-wrapper">
                {error && <ErrorBanner message={error} className="request-error" />}

                <div className="row">
                  <Field name="job" label="Profession" className="input" component={FormInput} />
                  <Field
                    name="company"
                    label="Entreprise"
                    className="input"
                    component={FormInput}
                  />
                </div>
                <div>
                  <TextArea name="description" label="Message" className="message-input" />
                </div>
                <div className="btn-wrapper">
                  <Button
                    type="submit"
                    text="Envoyer"
                    loading={loading}
                    disabled={!(isValid && dirty)}
                  />
                </div>
              </Form>
            )}
          </Formik>
        </>
      )}
    </div>
  );
};

GradeRequest.propTypes = {};

export default GradeRequest;
