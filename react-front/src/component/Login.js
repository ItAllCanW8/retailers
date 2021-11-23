import React, {Component} from 'react';
import {Button, Container, Form, FormGroup, Input, Label, Row} from 'reactstrap';
import axios from "axios";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        this.setState({
            [name]: value
        });
    }

    async handleSubmit(event) {
        event.preventDefault();
        const response = await axios.post('http://localhost:8080/auth/login', this.state);
        console.log(response.data.jwt);
        if (response.data.jwt) {
            localStorage.setItem("user", response.data.jwt);
        }
        return response.data;
    }

    render() {
        return <Container>
                    <Form onSubmit={this.handleSubmit}>
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