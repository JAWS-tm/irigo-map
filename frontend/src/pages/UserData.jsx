import React, { useState, useEffect } from 'react';
import { Formik, Form, ErrorMessage, Field } from 'formik';
import * as Yup from 'yup';
import UnderlinedTitle from '../components/UnderlinedTitle';
import FormInput from '../components/FormInput';
import Button from '../components/Button';
import {
  clearAuthError,
  getMe,
  register,
  selectAuthError,
  selectCurrentUser,
} from '../store/slices/authSlice';
import { useDispatch, useSelector } from 'react-redux';
import ErrorBanner from '../components/ErrorBanner';
import PopupError from '../components/PopupError';
import RadioGroup from '../components/Radio/RadioGroup';
import RadioOption from '../components/Radio/RadioOption';
import { UserSex } from '../constants';
import axios from 'axios';
import { config } from '../config/config';
import authHeader from '../services/auth-header';
import authService from '../services/auth.service';
import { useNavigate } from 'react-router-dom';
import jsPDF from 'jspdf';

//get values if is necessary
const initialValues = {
  firstName: '', //get firstName
  lastName: '', //get lastName
  email: '', //get email but can't be change
  password: '',
  confirmPassword: '',
  sex: '',
};

//reused validationSchema of Register
const validationSchema = Yup.object({
  firstName: Yup.string().max(30, 'Doit contenir 30 caractères ou moins'),
  lastName: Yup.string().max(30, 'Doit contenir 30 caractères ou moins'),
  password: Yup.string()
    .min(8, 'Votre mot de passe doit comporter plus de 8 caractères')
    .max(60)
    .matches(/\w*[a-z]\w*/, 'Le mot de passe doit avoir une lettre minuscule')
    .matches(/\w*[A-Z]\w*/, 'Le mot de passe doit avoir une majuscule')
    .matches(/\d/, 'Le mot de passe doit avoir un nombre')
    .matches(/[!@#$%^&*()\-_"=+{}; :,<.>àéèçù]/, 'Le mot de passe doit avoir un caractère spécial'),

  confirmPassword: Yup.string().oneOf(
    [Yup.ref('password'), null],
    'Les mots de passe doivent correspondre'
  ),
  sex: Yup.string(),
});

const UserData = (props) => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSubmit = (values, { setSubmitting }) => {
    // attention register est pour créer un compte
    dispatch(register(values));
  };

  //update changes
  const submitChanges = async (values) => {
    console.log(values);
    const AUTH_API_URL = config.API_URL + '/users/' + localStorage.getItem('email');
    let res = await axios.post(AUTH_API_URL, {
      firstName: values.firstName,
      lastName: values.lastName,
      password: values.password,
      sex: values.sex,
    });
    console.log(values.firstName);
  };

  //Delete user
  const submitDelete = async () => {
    const AUTH_API_URL = config.API_URL + '/users/' + localStorage.getItem('email');
    let res = await axios.delete(AUTH_API_URL, { headers: authHeader() }).then(() => {
      navigate('/logout');
    });
  };

  const userData = useSelector(selectCurrentUser);

  //print pdf of informations
  const print = () => {
    console.log('print pdf');
    //var image = new Image();
    //image.src = '../pictures/banière.png';
    const pdf = new jsPDF('p', 'px');

    pdf.addFont('helvetica', 'normal');
    //pdf.addImage(image, 'PNG');
    pdf.text('Bienvenue sur votre page information', 120, 20);
    pdf.text('Prénom', 30, 50);
    pdf.text(userData.firstName, 90, 50);
    pdf.text('Nom', 30, 70);
    pdf.text(userData.lastName, 90, 70);
    pdf.text('Email', 30, 90);
    pdf.text(userData.email, 90, 90);
    pdf.text('Sexe', 30, 110);
    pdf.text(userData.sex, 90, 110);
    pdf.text('Anniversaire', 30, 130);
    pdf.text(userData.birthday, 130, 130);
    pdf.text('Habitude de voyage', 30, 150);
    pdf.text(userData.travelHabits, 150, 150);
    pdf.text('Fréquence de voyage', 30, 170);
    pdf.text(userData.travelFrequency, 150, 170);
    pdf.text("EMPTY signifie que vous n'avez pas entré de donnée", 30, 210);
    pdf.save('infos.pdf');
  };

  const authError = useSelector(selectAuthError);

  return (
    <div className="User_data">
      <UnderlinedTitle className="title">Information Utilisateur</UnderlinedTitle>
      <div className="data" id="div">
        <br />
        <h3>Informations</h3>
        <Display_user_data></Display_user_data>
      </div>
      <div className="update" id="div">
        <h3>Modification du compte</h3>
        <div className="required">
          <br />
          <p>
            Le mot de passe doit contenir au moins 8 caractères. Attention, l'adresse mail n'est pas
            modifiable.
          </p>
          <p>
            Pour plus de sécurité, veillez à mettre au moins une Majuscule, un chiffre et un
            caractère spécial à votre mot de passe
          </p>
          <br />
        </div>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={handleSubmit}
        >
          <Form>
            {authError && <ErrorBanner message={authError} className="request-error" />}

            <div className="row">
              <Field name="firstName" label="Prénom " component={FormInput} />
              <Field name="lastName" label="Nom " component={FormInput} />
            </div>
            <div className="row">
              <Field
                name="password"
                label="Mot de passe "
                type="password"
                autoComplete="new-password"
                component={FormInput}
              />
              <Field
                name="confirmPassword"
                label="Confirmation "
                type="password"
                autoComplete="new-password"
                component={FormInput}
              />
            </div>
            <div className="row-title">
              <p>Genre</p>
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
            <div className="btn-wrapper">
              <br />
              <Button type="submit" onClick={submitChanges} text="Enregister" />
            </div>
          </Form>
        </Formik>
      </div>
      <div className="delete" id="div">
        <h3>Supprimer mon compte</h3>
        <br />
        <div className="infos">
          <p>
            Vous pouvez supprimer votre compte de manière définitive. Une fois que vous aurez pressé
            le bouton "Supprimer", votre compte n'existera plus.
          </p>
        </div>
        <br />
        <Button type="submit" onClick={submitDelete} text="Supprimer" />
        <br />
      </div>
      <div className="download" id="div">
        <h3>Télécharger les informations de mon compte</h3>
        <br />
        <div className="infos">
          <p>
            Les informations de votre compte sont disponible dans un document. Votre mot de passe
            n'apparaitera pas dans ce document pour mesure de sécurité. Pour télécharger le
            document, appuyer sur le bouton ci-dessous.
          </p>
        </div>
        <br />
        <Button type="button" onClick={print} text="Télécharger PDF" />
      </div>
    </div>
  );
}; //end register

UserData.propTypes = {};

export default UserData;

const initialValues_data = {
  firstName: 'Loading',
  lastName: 'Loading',
  email: 'Loading',
  sex: 'Loading',
};
const Display_user_data = () => {
  const dispatch = useDispatch();
  const userData = useSelector(selectCurrentUser);

  useEffect(() => {
    dispatch(getMe());
  }, []);

  //function get informations about user by his email
  // const AUTH_API_URL = config.API_URL + '/users/';
  // const fetch_ud = async (email) => {
  //   let res = await axios.get(AUTH_API_URL + email);
  //   setData(res.data.payload);
  // };

  // const [data, setData] = useState(initialValues_data); //return data and function(modif data)

  //at the begging (loading page)
  // useEffect(() => {
  //   fetch_ud(localStorage.getItem('email')); //get infos to date
  // }, []);

  return (
    <div className="infos">
      <br />
      <p>
        Prénom : <span>{userData.firstName}</span>
      </p>
      <p>
        Nom : <span>{userData.lastName}</span>
      </p>
      <p>
        Email : <span>{userData.email}</span>
      </p>
      <p>
        Sexe : <span>{userData.sex}</span>
      </p>
    </div>
  );
};
