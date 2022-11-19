import React, { useEffect } from 'react';
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
import { clearAuthError, register, selectAuthError } from '../store/slices/authSlice';
import { useDispatch, useSelector } from 'react-redux';
import ErrorBanner from '../components/ErrorBanner';

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
  email: Yup.string().email('Adresse e-mail invalide').required('Requis'),
  password: Yup.string()
    .required('Requis')
    .min(8, 'Votre mot de passe doit comporter plus de 8 caractères')
    .max(60)
    .matches(/\w*[a-z]\w*/, 'Le mot de passe doit avoir une lettre minuscule')
    .matches(/\w*[A-Z]\w*/, 'Le mot de passe doit avoir une majuscule')
    .matches(/\d/, 'Le mot de passe doit avoir un nombre')
    .matches(/[!@#$%^&*()\-_"=+{}; :,<.>àéèçù]/, 'Le mot de passe doit avoir un caractère spécial'),

  confirmPassword: Yup.string()
    .oneOf([Yup.ref('password'), null], 'Les mots de passe doivent correspondre')
    .required('Requis'),

  birthday: Yup.date()
    .min(moment().subtract(150, 'year'), "La date n'est pas valide")
    .max(moment(), "Vous ne pouvez pas être né après aujourd'hui")
    .test('valid', 'La date est invalide', (value) => {
      return moment(value).isValid();
    })
    .required('Requis'),
  sex: Yup.string().required('Requis'),
});

const Register = (props) => {
  const dispatch = useDispatch();
  const handleSubmit = (values, { setSubmitting }) => {
    dispatch(register(values));
  };

  useEffect(() => {
    return () => {
      console.log('clear');
      dispatch(clearAuthError());
    };
  }, []);

  const authError = useSelector(selectAuthError);

  return (
    <div className="Register">
      <UnderlinedTitle>Inscription</UnderlinedTitle>

      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form>
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
            <Field name="sex" label="Femme" radioValue={UserSex.FEMALE} component={RadioOption} />
            <Field name="sex" label="Autre" radioValue={UserSex.OTHER} component={RadioOption} />
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
            <Button type="submit" text="M'inscrire" />
          </div>
        </Form>
      </Formik>
    </div>
  );
};

Register.propTypes = {};

export default Register;
