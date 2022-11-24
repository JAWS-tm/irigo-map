import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import { Formik, Form, ErrorMessage, Field } from 'formik';
import { clearAuthError, register, selectAuthError } from '../store/slices/authSlice';
import { useDispatch, useSelector } from 'react-redux';
import ErrorBanner from '../components/ErrorBanner';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Button from '../components/Button';
import * as Yup from 'yup';
import PopupError from '../components/PopupError';

const initialValues = {
  firstName: '',
  lastName: '',
  password: '',
  confirmPassword: '',
};

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
});

const User_data = (props) => {
  return (
    <div className="User_data">
      <UnderlinedTitle className="title">Information Utilisateur</UnderlinedTitle>
      <div className="update">
        <Button onClick={PopupError} text="Enregistrer" />
        <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
        >
        <Form>{authError && <ErrorBanner message={authError} className="request-error" />}</Form>
      </div>
      <div className="delete">
        <Button onClick={PopupError} text="Supprimer" />
      </div>
      <div className="dowload">
        <Button type="submit" onClick={PopupError} text="Télécharger" />
      </div>
    </div>
  );
};

User_data.propTypes = {};

export default User_data;
