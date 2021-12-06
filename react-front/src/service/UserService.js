import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/';

class UserService {
  getPublicContent() {
    return axios.get(API_URL + 'all');
  }

  getUserBoard() {
    return axios.get(API_URL + 'user', {headers: authHeader()});
  }

  getModeratorBoard() {
    return axios.get(API_URL + 'mod', {headers: authHeader()});
  }

  getAdminBoard(params) {
    return axios.get(API_URL + 'system-admin', {headers: authHeader(), params: params});
  }
}

export default new UserService();