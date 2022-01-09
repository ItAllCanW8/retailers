import React, { Component, createRef } from 'react';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import * as bootstrap from 'bootstrap';
import Toast from '../common/Toast';
import Customers from './Customers';
import { ControlButtons } from './ControlButtons';
import { CustomerInnerModal } from './CustomerInnerModal';
import Modal from '../common/Modal';
import Util from '../../service/Util';
import AuthService from '../../service/AuthService';
import Pagination from '../common/Pagination';

class SystemAdmin extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      params: {
        page: 0,
        onlyActive: null
      },
      name: '',
      email: '',
      isFetching: false
    };
    this.handleChange = this.handleChange.bind(this);
    this.openModal = this.openModal.bind(this);
  }

  componentDidMount() {
    Util.redirectIfDoesntHaveRole(this, 'ROLE_SYSTEM_ADMIN');

    this.setState({ ...this.state, isFetching: true });
    this.updateCustomers();
  }

  updateCustomers() {
    axios.get('system-admin', { params: this.state.params }).then(
      (response) => {
        this.setState({
          ...this.state,
          content: response.data,
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

  submit = () => {
    this.modalRef.current.click();
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
  }

  openModal() {
    new bootstrap.Modal(this.modalRef.current).show();
  }

  radioChange = (event) => {
    const value = event.target.value;
    this.setState({
      params: {
        page: 0,
        onlyActive: value === 'Only active' || (value === 'All' && null)
      }
    }, () => this.updateCustomers());
  };

  render() {
    if (!AuthService.currentUserHasRole('ROLE_SYSTEM_ADMIN')) {
      return <Redirect to={"/"} />;
    }

    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <Modal
          ref={this.modalRef}
          submit={this.submit}
        >
          <CustomerInnerModal name={this.state.name} email={this.state.email} onChange={this.handleChange}/>
        </Modal>
        <ControlButtons
          onClick={this.openModal}
          onlyActive={this.state.params.onlyActive}
          onChange={this.radioChange}
        />

        {!this.state.isFetching && <Customers content={this.state.content && this.state.content.customers} />}
        <Pagination
          currentPage={this.state.params.page}
          totalPages={this.state.content && this.state.content.totalPages}
          toPage={(page) => Util.toPage(this, this.updateCustomers, page)}
        />
      </div>
    );
  }
}

export default SystemAdmin;
