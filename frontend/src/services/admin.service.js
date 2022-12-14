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

  async getRequests() {
    const res = await axiosClient.get('/admin/grade-requests', { headers: authHeader() });
    return res.data;
  }

  async validateRequest(id) {
    const res = await axiosClient.get(`/admin/grade-requests/${id}/validate`, {
      headers: authHeader(),
    });
    return res.data;
  }

  async removeRequest(id) {
    const res = await axiosClient.get(`/admin/grade-requests/${id}/remove`, {
      headers: authHeader(),
    });
    return res.data;
  }
}

export default new AdminService();
