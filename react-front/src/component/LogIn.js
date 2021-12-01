import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import axios from "axios";

const API_URL = "http://localhost:8080/api/";

class LogIn extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      loading: false,
      message: ''
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleLogin = this.handleLogin.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value
    });
  }

  handleLogin(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      this.state.isFormValidated = true;
      axios
        .post(API_URL + "login", this.state)
        .then(response => {
          if (response.data.jwt) {
            localStorage.setItem("user", JSON.stringify(response.data));
          }
          return response.data;
        }).then(
        () => {
          this.props.history.push("/profile");
          window.location.reload();
        },
        error => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            loading: false,
            message: resMessage
          });
        }
      );
    } else {
      this.setState({isFormValidated: false});
    }
  }

  render() {
    let isFormValidated = this.state.isFormValidated;
    return (<div className="container mt-4">
        <form className={isFormValidated || isFormValidated === undefined ? "needs-validation" : "needs-validation was-validated"}
              noValidate onSubmit={this.handleLogin}>
          <div className="row mb-3 justify-content-center">
            <label htmlFor="validationCustom01" className="form-label col-sm-2 col-form-label">Email</label>
            <div className="col-sm-4">
              <input type="email" name="email" className="form-control" id="validationCustom01" required
                     value={this.state.email || ''} onChange={this.handleChange} autoComplete="off"/>
                <div className="invalid-feedback">
                  Please, enter your email
                </div>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <label htmlFor="validationCustom02" className="form-label col-sm-2 col-form-label">Password</label>
            <div className="col-sm-4">
              <div className="input-group has-validation">
                <input type="password" name="password" className="form-control" id="validationCustom02" required
                       value={this.state.password || ''} onChange={this.handleChange} autoComplete="off"/>
                  <div className="invalid-feedback">
                    Please, enter your password
                  </div>
              </div>
            </div>
          </div>
          <div className="row mb-3 justify-content-center">
            <div className="col-sm-6">
              <button type="submit" className="col-sm-2 btn btn-primary">
                Login
              </button>
            </div>
          </div>
        </form>
    </div>);
  }
}

export default LogIn;