import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      email: '',
      password: '',
      loading: false,
      message: ""
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

  async handleLogin(event) {
    event.preventDefault();
    console.log(localStorage.getItem("user"));
    axios
      .post(API_URL + "signin", this.state)
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
    console.log(localStorage.getItem("user"));
  }

  render() {
    return <Container>
      <Form onSubmit={this.handleLogin}>
        <FormGroup>
          <Label for="email">Email</Label>
          <Input type="text" name="email" id="email" value={this.state.email || ''}
                 onChange={this.handleChange} autoComplete="email"/>
        </FormGroup>
        <FormGroup>
          <Label for="password">Password</Label>
          <Input type="text" name="password" id="password" value={this.state.password || ''}
                 onChange={this.handleChange} autoComplete="password"/>
        </FormGroup>
        <FormGroup>
          <Button color="primary" type="submit">Save</Button>
        </FormGroup>
      </Form>
    </Container>
    // <div className="container h-100">
    //     <div className="row h-100 justify-content-center align-items-center">
    //         <form className="form-signin" method="post" action="/auth/login">
    //             <div className="row mb-3 justify-content-center">
    //                 <h2 className="form-signin-heading">Login</h2>
    //                 <p>
    //                     <label htmlFor="username">Username</label>
    //                     <input type="text" id="username" name="username" className="form-control"
    //                            placeholder="Username"
    //                            required/>
    //                 </p>
    //                 <p>
    //                     <label htmlFor="password">Password</label>
    //                     <input type="password" id="password" name="password" className="form-control"
    //                            placeholder="Password" required/>
    //                 </p>
    //                 <button className="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    //             </div>
    //         </form>
    //     </div>
    // </div>

  }


}

export default Login;