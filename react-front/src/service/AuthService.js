import jwtDecode from 'jwt-decode';

class AuthService {
  getCurrentUser() {
    let user = localStorage.getItem('user');
    if (user) {
      return jwtDecode(user);
    }
  }

  authHeader() {
    const user = JSON.parse(localStorage.getItem('user'));

    if (user && user.jwt) {
      return { Authorization: 'Bearer ' + user.jwt };
    } else {
      return {};
    }
  }
}

export default new AuthService();
