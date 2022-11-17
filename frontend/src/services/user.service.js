import axios from 'axios';
import { API_URL } from '../config/config';
import authHeader from './auth-header';

const USER_API_URL = API_URL + '/users/';

class UserService {
  getAll() {
    return axios.get(USER_API_URL, { headers: authHeader() });
  }

  getUserBoard() {
    return axios.get(USER_API_URL + '1', { headers: authHeader() });
  }

  // getModeratorBoard() {
  //   return axios.get(USER_API_URL + 'mod', { headers: authHeader() });
  // }

  // getAdminBoard() {
  //   return axios.get(USER_API_URL + 'admin', { headers: authHeader() });
  // }
}

export default new UserService();
