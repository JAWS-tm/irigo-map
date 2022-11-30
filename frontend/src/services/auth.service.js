import axios from 'axios';
import { config } from '../config/config';
import authHeader from './auth-header';

const AUTH_API_URL = config.API_URL + '/auth/';

class AuthService {
  async login(email, password) {
    const response = await axios.post(AUTH_API_URL + 'login', { email, password });
    if (response.data.payload.accessToken) {
      console.log(response.data.payload.email);
      localStorage.setItem('email', response.data.payload.email);
      console.log(localStorage.getItem('email'));
      localStorage.setItem('token', response.data.payload.accessToken);
    }
    return response.data;
  }

  async register(
    email,
    password,
    lastName,
    firstName,
    sex,
    birthday,
    travelHabits,
    travelFrequency
  ) {
    const response = await axios.post(AUTH_API_URL + 'register', {
      email,
      password,
      lastName,
      firstName,
      sex,
      birthday,
      travelHabits,
      travelFrequency,
    });
    return response.data;
  }

  async getMe() {
    const response = await axios.get(AUTH_API_URL + 'me', { headers: authHeader() });
    return response.data;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('email');
  }
}

export default new AuthService();
