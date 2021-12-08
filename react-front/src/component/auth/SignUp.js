import React, { Component, createRef } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import * as bootstrap from 'bootstrap';
import Toast from '../common/Toast';

class SignUp extends Component {
  constructor(props) {
    super(props);
    this.toastRef = createRef();
    this.state = {
      name: '',
      email: '',
      password: '',
      role: 'SYSTEM_ADMIN',
      isSpinnerShown: false,
      message: '',
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSignUp = this.handleSignUp.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value,
    });
  }

  handleSignUp(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      this.setState({ isSpinnerShown: true });
      axios.post('signup', this.state).then(
        (response) => {
          this.setState({
            toastType: 'success',
            message: response.data.message,
            redirect: '/login',
          });
        },
        (error) => {
          this.setState({
            email: '',
            toastType: 'error',
            message: error.response.data.message,
            isSpinnerShown: false,
          });
          new bootstrap.Toast(this.toastRef.current).show();
        }
      );
    } else {
      this.setState({ isFormValidated: false });
    }
  }

  render() {
    if (this.state.redirect) {
      return (
        <Redirect
          to={{
            pathname: this.state.redirect,
            state: { message: this.state.message },
          }}
        />
      );
    }

    let isFormValidated = this.state.isFormValidated;
    return (
      <div className="container mt-4">
        <div className="row mb-4 justify-content-center text-center">
          <h3 className="col">Sign Up</h3>
        </div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <form
          noValidate
          onSubmit={this.handleSignUp}
          className={
            isFormValidated || isFormValidated === undefined
              ? 'needs-validation'
              : 'needs-validation was-validated'
          }
        >
          <div className="row mb-3 justify-content-center">
            <label
              htmlFor="name"
              className="form-label col-sm-1 col-form-label"
            >
              Name
            </label>
            <div className="col-sm-4">
              <input
                type="text"
                name="name"
                className="form-control"
                id="name"
                required
                value={this.state.name || ''}
                onChange={this.handleChange}
                autoComplete="off"
              />
              <div className="invalid-feedback">Please, enter your name</div>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <label
              htmlFor="email"
              className="form-label col-sm-1 col-form-label"
            >
              Email
            </label>
            <div className="col-sm-4">
              <input
                type="email"
                name="email"
                className="form-control"
                id="email"
                required
                value={this.state.email || ''}
                onChange={this.handleChange}
                autoComplete="off"
              />
              <div className="invalid-feedback">Please, enter valid email</div>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <label
              htmlFor="role"
              className="form-label col-sm-1 col-form-label"
            >
              Role
            </label>
            <div className="col-sm-4">
              <select
                className="form-select"
                aria-label="Default select example"
                id="role"
                name="role"
                value={this.state.role || ''}
                onChange={this.handleChange}
              >
                <option value="SYSTEM_ADMIN">SYSTEM_ADMIN</option>
                <option value="DIRECTOR">DIRECTOR</option>
              </select>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <label
              htmlFor="password"
              className="form-label col-sm-1 col-form-label"
            >
              Password
            </label>
            <div className="col-sm-4">
              <div className="input-group has-validation">
                <input
                  type="password"
                  name="password"
                  className="form-control"
                  id="password"
                  required
                  value={this.state.password || ''}
                  onChange={this.handleChange}
                  autoComplete="off"
                />
                <div className="invalid-feedback">
                  Please, enter your password
                </div>
              </div>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <div className="col-sm-5">
              <div className="row align-items-center">
                <div className="col-auto">
                  <button type="submit" className="btn btn-primary">
                    Sign Up
                  </button>
                </div>
                <div className="col-auto">
                  <div
                    className="spinner-border"
                    style={this.state.isSpinnerShown ? {} : { display: 'none' }}
                    role="status"
                  >
                    <span className="visually-hidden">Loading...</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default SignUp;
