import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import axios from "axios";

const API_URL = "http://localhost:8080/api/";

class SignUp extends Component {
  constructor(props) {
    super(props);
    this.state = {
      username: "",
      email: "",
      password: "",
      role: "SYSTEM_ADMIN"
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
    axios.post(API_URL + "signup", this.state);

  }

  render() {
    return (
      <Container>
        <Form onSubmit={this.handleLogin}>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={this.state.name || ''}
                   onChange={this.handleChange} autoComplete="off"/>
          </FormGroup>
          <FormGroup>
            <Label for="email">Email</Label>
            <Input type="text" name="email" id="email" value={this.state.email || ''}
                   onChange={this.handleChange} autoComplete="off"/>
          </FormGroup>
          <FormGroup>
            <Label for="role">Role</Label>
            <Input type="select" name="role" id="role" value={this.state.role || ''}
                   onChange={this.handleChange}>
              <option>RETAIL_ADMIN</option>
              <option>SYSTEM_ADMIN</option>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="password">Password</Label>
            <Input type="password" name="password" id="password" value={this.state.password || ''} autoComplete="off"
                   onChange={this.handleChange}/>
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>
          </FormGroup>
        </Form>
      </Container>
    );
  }
}

export default SignUp;