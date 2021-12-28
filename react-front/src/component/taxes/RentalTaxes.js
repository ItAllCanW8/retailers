import React, { Component, createRef } from 'react';
import axios from 'axios';
import Util from '../../service/Util';
import AuthService from '../../service/AuthService';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';

class RentalTaxes extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      locations: [{
        type: '',
        rentalTaxRate: ''
      }]
    };
  }

  componentDidMount() {
    this.updateTaxes();
  }

  updateTaxes = () => {
    axios.get('/locations').then(
      (response) => {
        this.setState({
          locations: response.data
        });
      }
    );
  };

  submit = () => {
    axios.put("/taxes/rental", this.state.locations).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateTaxes();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
  }

  render() {
    if (!AuthService.currentUserHasRole('ROLE_DIRECTOR')) {
      return <Redirect to={"/"} />;
    }
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <div className='row align-items-center mb-3'>
          <div className='col-auto align-items-center'>
            <button
              type='button'
              className='btn btn-primary me-3'
              onClick={this.submit}
            >
              Save
            </button>
          </div>
        </div>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>Location</th>
            <th scope='col'>Tax, %</th>
          </tr>
          </thead>
          <tbody>
          {this.state.locations && this.state.locations.map((location, index) => {
            if (location.type === 'OFFLINE_SHOP') return (
              <tr key={location.id}>
                <th scope='row'>

                </th>
                <td>{location.identifier}</td>
                <td>
                  <input
                    type='number'
                    className='form-control'
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    min='0'
                    step='0.01'
                    style={{ width: 130 }}
                    value={location.rentalTaxRate}
                    onChange={() => Util.handleRentalTaxChange(this, window.event.target.value, index)}
                  />
                </td>
              </tr>
            );
          })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default RentalTaxes;