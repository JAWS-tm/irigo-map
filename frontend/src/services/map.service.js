import { config } from '../config/config';
import { axiosClient } from '.';

const BUS_API_URL = config.API_URL + '/bus';

class MapService {
  getBus() {
    return axiosClient
      .get(BUS_API_URL)
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getStops() {
    return axiosClient
      .get(BUS_API_URL + '/stops')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getLines() {
    return axiosClient
      .get(BUS_API_URL + '/lines')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }

  getStopTimetable(id) {
    return axiosClient
      .get(BUS_API_URL + '/stops/' + id + '/timetable')
      .then((res) => res.data?.payload)
      .catch((err) => console.log(err));
  }
}

export default new MapService();
