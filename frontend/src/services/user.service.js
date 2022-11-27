import axios from 'axios';
import { config } from '../config/config';
import authHeader from './auth-header';

const USER_API_URL = config.API_URL + '/users/';

class UserService {
  getAll() {
    return axios.get(USER_API_URL, { headers: authHeader() });
  }

  // getUserBoard() {
  //   return axios.get(USER_API_URL + '1', { headers: authHeader() });
  // }
}

export default new UserService();
