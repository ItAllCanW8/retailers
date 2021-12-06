import React, {Component, createRef} from 'react';
import UserService from "../service/UserService";
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
      inlineRadioOptions: "All",
      redirect: null,
      isFetching: false
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
    this.setState({...this.state, isFetching: true});
    UserService.getAdminBoard().then(
      response => {
        this.setState({
          ...this.state,
          content: response.data,
          isFetching: false
        });
      },
      error => {
        this.setState({
          ...this.state,
          content: error.response.data.message,
          isFetching: false
        });
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
            UserService.getAdminBoard().then(
              response => {
                this.setState({
                  ...this.state,
                  content: response.data,
                  isFetching: false
                });
              },
              error => {
                this.setState({
                  ...this.state,
                  content: error.response.data.message,
                  isFetching: false
                });
              }
            );
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

  radioChange = (event) => {
    const value = event.target.value;
    this.setState({
      inlineRadioOptions: value
    });
    UserService.getAdminBoard({isOnlyActive: value === 'Only active' || value === 'All' && null}).then(
      response => {
        this.setState({
          ...this.state,
          content: response.data,
          isFetching: false
        });
      },
      error => {
        this.setState({
          ...this.state,
          content: error.response.data.message,
          isFetching: false
        });
      }
    );
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect}/>
    }
    let isFormValidated = this.state.isFormValidated;
    return (
      <div>
        <div className="row align-items-center mb-3">
          <div className="col align-items-center">
            <button type="button" className="btn btn-primary me-3" onClick={this.openModal}>
              Add customer
            </button>
            <div className="form-check form-check-inline">
              <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="All"
                     checked={this.state.inlineRadioOptions === 'All'} onChange={this.radioChange}/>
              <label className="form-check-label" htmlFor="inlineRadio1">All</label>
            </div>
            <div className="form-check form-check-inline">
              <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="Only active"
                     checked={this.state.inlineRadioOptions === 'Only active'} onChange={this.radioChange}/>
              <label className="form-check-label" htmlFor="inlineRadio2">Only active</label>
            </div>
            <div className="form-check form-check-inline">
              <input className="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio3" value="Only inactive"
                     checked={this.state.inlineRadioOptions === 'Only inactive'} onChange={this.radioChange}/>
              <label className="form-check-label" htmlFor="inlineRadio3">Only inactive</label>
            </div>
          </div>
        </div>

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
        {!this.state.isFetching && <Customers content={this.state.content}/>}
      </div>
    );
  }
}

export default BoardAdmin;