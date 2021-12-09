import React, { Component, createRef } from 'react';
import { Redirect } from 'react-router-dom';
import * as bootstrap from 'bootstrap';
import Modal from '../common/Modal';
import { LocationsInnerModal } from './LocationsInnerModal';
import axios from 'axios';
import Toast from '../common/Toast';
import AuthService from '../../service/AuthService';

class Locations extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      locations: [],
      ids: [],
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

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser || !currentUser.role.includes('ADMIN')) {
      this.setState({ redirect: '/' });
      return;
    }

    this.setState({ ...this.state, isFetching: true });
    this.updateLocations();
  }

  updateLocations = () => {
    axios.get('admin/locations').then(
      (response) => {
        this.setState({
          ...this.state,
          locations: response.data,
          isFetching: false
        });
      },
      (error) => {
        this.setState({
          ...this.state,
          content: error.response.data.message,
          isFetching: false
        });
      }
    );
  };

  submit = () => {
    this.modalRef.current.click();
    axios.post('admin/locations', this.state).then(
      (response) => {
        this.setState({
          toastType: 'success',
          message: response.data.message
        });
        this.updateLocations();
        new bootstrap.Toast(this.toastRef.current).show();
      },
      (error) => {
        this.setState({
          toastType: 'error',
          message: error.response.data.message
        });
        new bootstrap.Toast(this.toastRef.current).show();
      }
    );
  };

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

  handleLocationSelection = (event, id) => {
    if (event.target.checked) {
      this.setState({
        ids: [...this.state.ids, id]
      });
    } else {
      let array = this.state.ids;
      let index = array.indexOf(id);
      if (index !== -1) {
        array.splice(index, 1);
      }
      this.setState({
        ids: array
      });
    }

  };

  deleteLocations = () => {
    axios.delete("admin/locations", {
      data: this.state.ids
    })
    .then(this.updateLocations);
    this.setState({ids: []});
  }

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
              className='btn btn-primary btn-danger me-3'
              disabled={this.state.ids.length === 0}
              onClick={this.deleteLocations}
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
                               totalCapacity={this.state.totalCapacity}
                               availableCapacity={this.state.availableCapacity} />
        </Modal>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>Identifier</th>
            <th scope='col'>Type</th>
            <th scope='col'>State code</th>
            <th scope='col'>City</th>
            <th scope='col'>Address line 1</th>
            <th scope='col'>Address line 2</th>
            <th scope='col'>Available</th>
          </tr>
          </thead>
          <tbody>
          {this.state.locations && this.state.locations.map((location) => (
            <tr key={location.id}>
              <th scope='row'>
                <input className='form-check-input' type='checkbox' value={location.id}
                       onChange={() => this.handleLocationSelection(window.event, location.id)} />
              </th>
              <td>{location.identifier}</td>
              <td>
                {location.type === 'WAREHOUSE'
                  ? <img src='https://svgshare.com/i/ce2.svg' alt='shop-icon' style={{ width: 30 }} />
                  : <img src='https://svgshare.com/i/ceL.svg' alt='shop-icon' style={{ width: 30 }} />
                }
              </td>
              <td>{location.address.stateCode}</td>
              <td>{location.address.city}</td>
              <td>{location.address.firstLine}</td>
              <td>{location.address.secondLine}</td>
              <td>{location.availableCapacity}/{location.totalCapacity}</td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Locations;