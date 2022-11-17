import React from 'react';
import UnderlinedTitle from '../components/UnderlinedTitle';
import Input from '../components/Input';
import Button from '../components/Button';
import { useDispatch } from 'react-redux';
import { login } from '../store/slices/authSlice';
import * as Yup from 'yup';
import { Field, Form, Formik } from 'formik';
import FormInput from '../components/FormInput';

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
    console.log(values);
    dispatch(login(values));
  };

  return (
    <div className="Login">
      <UnderlinedTitle>Se connecter</UnderlinedTitle>
      <Formik
        initialValues={initialValues}
        validationSchema={validationSchema}
        onSubmit={handleSubmit}
      >
        <Form>
          <Field name="email" label="Email" className="input" component={FormInput} />
          <Field name="password" label="Mot de passe" className="input" component={FormInput} />

          {/* <Input label="Email" className="input" type={'email'} />
          <Input label="Mot de passe" className="input" type={'password'} /> */}

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
