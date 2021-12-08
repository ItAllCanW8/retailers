import React, { Component, createRef } from 'react';
import { Redirect } from 'react-router-dom';
import * as bootstrap from 'bootstrap';
import Modal from '../common/Modal';
import { LocationsInnerModal } from './LocationsInnerModal';
import axios from 'axios';
import Toast from '../common/Toast';

class Locations extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      identifier: '',
      type: 'WAREHOUSE',
      address: {
        stateCode: '',
        city: '',
        firstLine: '',
        secondLine: ''
      },
      totalCapacity: null,
      availableCapacity: null,
      redirect: null
    };
  }

  submit = () => {
    this.modalRef.current.click();
    axios.post('admin/locations', this.state).then(
      (response) => {
        this.setState({
          toastType: 'success',
          message: response.data.message,
        });
        new bootstrap.Toast(this.toastRef.current).show();
      },
      (error) => {
        this.setState({
          toastType: 'error',
          message: error.response.data.message,
        });
        new bootstrap.Toast(this.toastRef.current).show();
      }
    );
  }

  handleChange = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    let optionalIntValue = parseInt(value);
    if (!isNaN(optionalIntValue)) {
      value = optionalIntValue;
    }
    this.setState({
      [name]: value
    });
  };

  handleAddressChange = (event) => {
    const target = event.target;
    this.setState({
      address: {
        ...this.state.address,
        [target.name]: target.value
      }
    });
  };

  openModal = () => {
    new bootstrap.Modal(this.modalRef.current).show();
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
        <div className='row align-items-center mb-3'>
          <div className='col align-items-center'>
            <button
              type='button'
              className='btn btn-primary me-3'
              onClick={this.openModal}
            >
              Add
            </button>
            <button
              type='button'
              className='btn btn-primary btn-danger disabled me-3'
              onClick={this.props.onClick}
            >
              Remove
            </button>
          </div>
        </div>
        <Modal
          ref={this.modalRef}
          submit={this.submit}
        >
          <LocationsInnerModal identifier={this.state.identifier} type={this.state.type}
                               onChange={this.handleChange} onAddressChange={this.handleAddressChange}
                               stateCode={this.state.address.stateCode} city={this.state.address.city}
                               firstLine={this.state.address.firstLine} secondLine={this.state.address.secondLine}
                               totalCapacity={this.state.totalCapacity} availableCapacity={this.state.availableCapacity}/>
        </Modal>
      </div>
    );
  }
}

export default Locations;