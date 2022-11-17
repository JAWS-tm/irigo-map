import axios from 'axios';
import { API_URL } from '../config/config';

const AUTH_API_URL = API_URL + '/auth/';

class AuthService {
  login(email, password) {
    return axios.post(AUTH_API_URL + 'login', { email, password }).then((response) => {
      console.log(response);

      if (response.data.accessToken) {
        localStorage.setItem('user', JSON.stringify(response.data));
      }

      return response.data;
    });
  }

  register(email, password, lastName, firstName, sex, birthday, travelHabits, travelFrequency) {
    return axios
      .post(AUTH_API_URL + 'register', {
        email,
        password,
        lastName,
        firstName,
        sex,
        birthday,
        travelHabits,
        travelFrequency,
      })
      .then((response) => {
        console.log('register response', response);

        return response.data;
      });
  }

  // logout() {
  //   localStorage.removeItem("user");
  // }
}

export default new AuthService();
