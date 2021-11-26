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

  async handleLogin(event) {
    event.preventDefault();
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
  }
}

export default LogIn;