import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import "bootstrap/dist/css/bootstrap.min.css";
import 'bootstrap/dist/js/bootstrap.min.js'
import 'bootstrap/dist/js/bootstrap.bundle.js'
import axios from "axios";
import AuthService from './service/AuthService';

axios.defaults.baseURL = 'http://localhost:8080/api/';
axios.defaults.headers.common['Authorization'] = AuthService.authHeader().Authorization;
axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.defaults.headers.put['Content-Type'] = 'application/json';

ReactDOM.render(
  <App/>,
  document.getElementById('root')
);

