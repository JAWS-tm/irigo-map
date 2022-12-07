import axios from 'axios';
import { config } from '../config/config';
import authHeader from './auth-header';

const USER_API_URL = config.API_URL + '/users';

class UserService {
  getAll() {
    return axios.get(USER_API_URL, { headers: authHeader() });
  }

  forgotPassword(email) {
    return axios.post(USER_API_URL + '/forgot-password', { email });
  }

  validatePasswordToken(token) {
    return axios.get(USER_API_URL + '/validate-password-token/' + token);
  }

  resetPassword(token, newPassword) {
    return axios.post(USER_API_URL + '/reset-password', { token, password: newPassword });
  }

  // getUserBoard() {
  //   return axios.get(USER_API_URL + '1', { headers: authHeader() });
  // }
}

export default new UserService();
