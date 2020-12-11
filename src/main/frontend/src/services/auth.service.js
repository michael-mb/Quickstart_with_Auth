import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'signin', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
        return axios.get( API_URL + 'logout');
  }

  register(form) {
    return axios.post(API_URL + 'signup', {
      email: form.email,
      pseudo: form.pseudo,
      firstName: form.firstName,
      lastName: form.lastName,
      password: form.password,
      city: form.city,
      passwordAgain: form.passwordAgain,
    });
  }
}

export default new AuthService();
