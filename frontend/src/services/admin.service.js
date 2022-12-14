import axios from 'axios';
import { axiosClient } from '.';
import { config } from '../config/config';
import authHeader from './auth-header';

class AdminService {
  async getUsers() {
    const res = await axiosClient.get('/users', { headers: authHeader() });
    if (res.data.status === 'OK') return res.data?.payload;
    else return null;
  }

  async changeRole(id, role) {
    const res = await axiosClient.get('/admin/users/upgrade/' + id + '/' + role, {
      headers: authHeader(),
    });
    if (res.data.status === 'OK') return true;
    return false;
  }

  async deleteUser(id) {
    const res = await axiosClient.get('/admin/users/delete/' + id, { headers: authHeader() });
    if (res.data.status === 'OK') return true;
    return false;
  }
}

export default new AdminService();
