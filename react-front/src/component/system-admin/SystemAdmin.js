import React, { Component, createRef } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import AuthService from '../../service/AuthService';
import * as bootstrap from 'bootstrap';
import Toast from '../common/Toast';
import Customers from './Customers';
import { ControlButtons } from './ControlButtons';
import { Modal } from './Modal';

class SystemAdmin extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      name: '',
      email: '',
      radioOption: 'All',
      redirect: null,
      isFetching: false,
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.openModal = this.openModal.bind(this);
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser || !currentUser.role.includes('SYSTEM_ADMIN')) {
      this.setState({ redirect: '/' });
      return;
    }

    this.setState({ ...this.state, isFetching: true });
    this.updateCustomers();
  }

  updateCustomers(params) {
    axios.get('system-admin', { params: params }).then(
      (response) => {
        this.setState({
          ...this.state,
          content: response.data,
          isFetching: false,
        });
      },
      (error) => {
        this.setState({
          ...this.state,
          content: error.response.data.message,
          isFetching: false,
        });
      }
    );
  }

  handleChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value,
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      document.getElementById('customerModal').click();
      axios.post('system-admin', this.state).then(
        (response) => {
          this.setState({
            email: '',
            toastType: 'success',
            message: response.data.message,
          });
          this.updateCustomers();
          new bootstrap.Toast(this.toastRef.current).show();
        },
        (error) => {
          this.setState({
            email: '',
            toastType: 'error',
            message: error.response.data.message,
          });
          new bootstrap.Toast(this.toastRef.current).show();
        }
      );
    } else {
      this.setState({ isFormValidated: false });
    }
  }

  openModal() {
    new bootstrap.Modal(document.getElementById('customerModal')).show();
  }

  radioChange = (event) => {
    const value = event.target.value;
    this.setState({
      radioOption: value,
    });
    this.updateCustomers({
      isOnlyActive: value === 'Only active' || (value === 'All' && null),
    });
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <ControlButtons
          onClick={this.openModal}
          radioOption={this.state.radioOption}
          onChange={this.radioChange}
        />
        <Modal
          ref={this.modalRef}
          formValidated={this.state.isFormValidated}
          onSubmit={this.handleSubmit}
          name={this.state.name}
          onChange={this.handleChange}
          email={this.state.email}
        />
        {!this.state.isFetching && <Customers content={this.state.content} />}
      </div>
    );
  }
}

export default SystemAdmin;
