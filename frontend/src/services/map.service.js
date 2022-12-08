import axios from 'axios';
import { config } from '../config/config';
import authHeader from './auth-header';

const BUS_API_URL = config.API_URL + '/bus';

const busApi = axios.create({ baseURL: BUS_API_URL, headers: authHeader() });

class MapService {
  getBus() {
    return busApi
      .get('')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getStops() {
    return busApi
      .get('/stops')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getLines() {
    return busApi
      .get('/lines')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getStopTimetable(id) {
    return busApi
      .get('/stops/' + id + '/timetable')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }
}

export default new MapService();
