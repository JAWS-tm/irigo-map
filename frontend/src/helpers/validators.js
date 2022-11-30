import * as Yup from 'yup';

export const passwordValidator = Yup.string()
  .required('Requis')
  .min(8, 'Votre mot de passe doit comporter plus de 8 caractères')
  .max(60)
  .matches(/\w*[a-z]\w*/, 'Le mot de passe doit avoir une lettre minuscule')
  .matches(/\w*[A-Z]\w*/, 'Le mot de passe doit avoir une majuscule')
  .matches(/\d/, 'Le mot de passe doit avoir un nombre')
  .matches(/[!@#$%^&*()\-_"=+{}; :,<.>àéèçù]/, 'Le mot de passe doit avoir un caractère spécial');

export const confirmPasswordValidator = Yup.string()
  .oneOf([Yup.ref('password'), null], 'Les mots de passe doivent correspondre')
  .required('Requis');

export const emailValidator = Yup.string().email('Adresse e-mail invalide').required('Requis');
