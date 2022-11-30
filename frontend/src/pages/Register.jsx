import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { Formik, Form, ErrorMessage, Field } from 'formik';
import * as Yup from 'yup';
import UnderlinedTitle from '../components/UnderlinedTitle';
import FormInput from '../components/FormInput';
import Button from '../components/Button';
import RadioGroup from '../components/Radio/RadioGroup';
import RadioOption from '../components/Radio/RadioOption';
import { TravelFrequency, TravelHabits, UserSex } from '../constants';
import DatePicker from '../components/DatePicker';
import moment from 'moment';
import {
  clearAuthError,
  register,
  selectAuthError,
  selectAuthIsLoading,
} from '../store/slices/authSlice';
import { useDispatch, useSelector } from 'react-redux';
import ErrorBanner from '../components/ErrorBanner';
import { confirmPasswordValidator, emailValidator, passwordValidator } from '../helpers/validators';
import { useNavigate } from 'react-router-dom';
import SuccessMessage from '../components/SuccessMessage';

const initialValues = {
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  confirmPassword: '',
  birthday: '',
  sex: '',
  travelHabits: TravelHabits.EMPTY,
  travelFrequency: TravelFrequency.EMPTY,
};

const validationSchema = Yup.object({
  firstName: Yup.string().max(30, 'Doit contenir 30 caractères ou moins').required('Requis'),
  lastName: Yup.string().max(30, 'Doit contenir 30 caractères ou moins').required('Requis'),
  email: emailValidator,
  password: passwordValidator,
  confirmPassword: confirmPasswordValidator,
  birthday: Yup.date()
    .min(moment().subtract(150, 'year'), "La date n'est pas valide")
    .max(moment().subtract(16, 'year'), 'Vous devez avoir au minimum 16 ans')
    .test('valid', 'La date est invalide', (value) => {
      return moment(value).isValid();
    })
    .required('Requis'),
  sex: Yup.string().required('Requis'),
});

const Register = (props) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [isRegistered, setIsRegistered] = useState(false);

  const authError = useSelector(selectAuthError);
  const loading = useSelector(selectAuthIsLoading);

  const handleSubmit = (values, { setSubmitting }) => {
    if (loading) return;
    dispatch(register(values)).then(() => {
      setIsRegistered(true);
    });
  };

  useEffect(() => {
    return () => {
      dispatch(clearAuthError());
    };
  }, []);

  return (
    <div className="Register page-wrapper">
      <UnderlinedTitle>Inscription</UnderlinedTitle>

      {isRegistered ? (
        <SuccessMessage
          message="Votre compte à bien été créée, veuillez vous connecter."
          buttonText="Connexion"
          buttonClick={() => navigate('/sign-in')}
        />
      ) : (
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          {({ isValid, dirty }) => (
            <Form className="form-wrapper">
              <div style={{ fontSize: '14px', marginBottom: '20px' }}>
                <p>
                  Les élements avec le caractère * sont obligatoires. Le mot de passe doit contenir
                  au moins 8 caractères
                </p>
                <p>
                  Pour plus de sécurité, veillez à mettre au moins une majuscule, un chiffre et un
                  caractère spécial à votre mot de passe
                </p>
              </div>
              {authError && <ErrorBanner message={authError} className="request-error" />}

              <div className="row">
                <Field name="firstName" label="Prénom *" component={FormInput} />
                <Field name="lastName" label="Nom *" component={FormInput} />
              </div>

              <Field name="email" label="Email *" component={FormInput} />

              <div className="row">
                <Field
                  name="password"
                  label="Mot de passe *"
                  type="password"
                  autoComplete="new-password"
                  component={FormInput}
                />
                <Field
                  name="confirmPassword"
                  label="Confirmation *"
                  type="password"
                  autoComplete="new-password"
                  component={FormInput}
                />
              </div>

              <div className="row-title">
                <p>Date de naissance *</p>
              </div>

              <Field name="birthday" component={DatePicker} />

              <div className="row-title">
                <p>Genre *</p>
              </div>
              <RadioGroup>
                <Field name="sex" label="Homme" radioValue={UserSex.MALE} component={RadioOption} />
                <Field
                  name="sex"
                  label="Femme"
                  radioValue={UserSex.FEMALE}
                  component={RadioOption}
                />
                <Field
                  name="sex"
                  label="Autre"
                  radioValue={UserSex.OTHER}
                  component={RadioOption}
                />
                <Field
                  name="sex"
                  label="Non précisé"
                  radioValue={UserSex.UNKNOWN}
                  component={RadioOption}
                />
              </RadioGroup>
              <ErrorMessage name="sex">{(msg) => <div className="error">{msg}</div>}</ErrorMessage>

              <div className="row-title">
                <p>Habitudes de déplacement</p>
              </div>
              <RadioGroup>
                <Field
                  name="travelHabits"
                  label="À pied"
                  radioValue={TravelHabits.FOOT}
                  component={RadioOption}
                />
                <Field
                  name="travelHabits"
                  label="Vélo"
                  radioValue={TravelHabits.BICYCLE}
                  component={RadioOption}
                />
                <Field
                  name="travelHabits"
                  label="Voiture"
                  radioValue={TravelHabits.CAR}
                  component={RadioOption}
                />
                <Field
                  name="travelHabits"
                  label="Transports"
                  radioValue={TravelHabits.TRANSPORT}
                  component={RadioOption}
                />
              </RadioGroup>
              <ErrorMessage name="travelHabits">
                {(msg) => <div className="error">{msg}</div>}
              </ErrorMessage>

              <div className="row-title">
                <p>Fréquence d'utilisation des transports</p>
              </div>
              <RadioGroup className="transport-radios">
                <Field
                  name="travelFrequency"
                  component={RadioOption}
                  label="Quotidien"
                  radioValue={TravelFrequency.DAILY}
                />
                <Field
                  name="travelFrequency"
                  component={RadioOption}
                  label="Hebdomadaire"
                  radioValue={TravelFrequency.WEEKLY}
                />
                <Field
                  name="travelFrequency"
                  component={RadioOption}
                  label="Mensuel"
                  radioValue={TravelFrequency.MONTHLY}
                />
                <Field
                  name="travelFrequency"
                  component={RadioOption}
                  label="Épisodique"
                  radioValue={TravelFrequency.EPISODIC}
                />
                <Field
                  name="travelFrequency"
                  component={RadioOption}
                  label="Jamais"
                  radioValue={TravelFrequency.NEVER}
                />
              </RadioGroup>
              <ErrorMessage name="travelFrequency">
                {(msg) => <div className="error">{msg}</div>}
              </ErrorMessage>

              <div className="btn-wrapper">
                <Button
                  type="submit"
                  text="M'inscrire"
                  loading={loading}
                  disabled={!(isValid && dirty)}
                />
              </div>
            </Form>
          )}
        </Formik>
      )}
    </div>
  );
};

Register.propTypes = {};

export default Register;
