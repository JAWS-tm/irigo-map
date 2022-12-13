import axios from 'axios';
import { config } from '../config/config';

export const axiosClient = axios.create({
  baseURL: config.API_URL,
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
});

axiosClient.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error) {
    let res = error.response;
    if (res.status == 401 && localStorage.getItem('token') !== null) {
      // logout if unauthorized
      console.log('401 redirect');
      window.location.href = config.FRONT_URL + '/logout';
    }

    if (res.status == 403) {
      // logout if unauthorized
      console.log('403 forbiden redirect');
      window.location.href = config.FRONT_URL;
    }
    console.error('Looks like there was a problem. Status Code: ' + res.status);
    return Promise.reject(error);
  }
);
