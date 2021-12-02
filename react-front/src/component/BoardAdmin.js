import React, {Component, createRef} from 'react';
import UserService from "../service/UserService";
import EventBus from "../common/EventBus";
import axios from "axios";
import {Redirect} from "react-router-dom";
import AuthService from "../service/AuthService";
import * as bootstrap from "bootstrap";
import Toast from "./Toast";
import Customers from "./Customers";

const API_URL = "http://localhost:8080/api/";

class BoardAdmin extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      name: "",
      email: "",
      redirect: null
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.openModal = this.openModal.bind(this);
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser || !currentUser.role.includes("SYSTEM_ADMIN")) {
      this.setState({redirect: "/"});
      return;
    }
    UserService.getAdminBoard().then(
      response => {
        this.setState({
          content: response.data
        });
        console.log(this.state);
      },
      error => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      }
    );
  }

  handleChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      document.getElementById('customerModal').click();
      axios.post(API_URL + "system-admin", this.state)
        .then((response) => {
            this.setState({
              email: "",
              toastType: "success",
              message: response.data.message
            });
            new bootstrap.Toast(this.toastRef.current).show();
          },
          error => {
            this.setState({
              email: "",
              toastType: "error",
              message: error.response.data.message
            });
            new bootstrap.Toast(this.toastRef.current).show();
          });
    } else {
      this.setState({isFormValidated: false});
    }
  }

  openModal() {
    new bootstrap.Modal(document.getElementById('customerModal')).show();
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect}/>
    }
    let isFormValidated = this.state.isFormValidated;
    return (
      <div>
        <button type="button" className="btn btn-primary mb-3" onClick={this.openModal}>
          Add customer
        </button>
        <Toast toastType={this.state.toastType} message={this.state.message} ref={this.toastRef}/>
        <div className="modal fade" id="customerModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true" ref={this.modalRef}>
          <div className="modal-dialog">
            <form className={isFormValidated || isFormValidated === undefined
              ? "row g-3 needs-validation" : "row g-3 needs-validation was-validated"}
                  noValidate onSubmit={this.handleSubmit}>
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title" id="exampleModalLabel">Add customer</h5>
                  <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
                </div>
                <div className="modal-body">

                  <div className="col mb-2">
                    <label htmlFor="validationCustomUsername" className="form-label">Username</label>
                    <div className="input-group has-validation">
                      <input type="text" className="form-control" name="name" id="name"
                             aria-describedby="inputGroupPrepend" required autoComplete="off"
                             value={this.state.name || ''} onChange={this.handleChange}/>
                      <div className="invalid-feedback">
                        Please choose a username.
                      </div>
                    </div>
                  </div>
                  <div className="col">
                    <label htmlFor="validationCustom03" className="form-label">Email</label>
                    <input type="email" className="form-control" name="email" id="email" required autoComplete="off"
                           value={this.state.email || ''} onChange={this.handleChange}/>
                    <div className="invalid-feedback">
                      Please enter a valid email
                    </div>
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="submit" className="btn btn-primary">Add</button>
                </div>
              </div>
            </form>
          </div>
        </div>
        <Customers content={this.state.content}/>
      </div>
    );
  }
}

export default BoardAdmin;