import React, { Component } from 'react';
import { Autocomplete, TextField } from '@mui/material';

export class UsersInnerModal extends Component {
  render() {
    return <div className='modal-content'>
      <div className='modal-header'>
        <h5 className='modal-title' id='exampleModalLabel'>
          Add user
        </h5>
        <button
          type='button'
          className='btn-close'
          data-bs-dismiss='modal'
          aria-label='Close'
        />
      </div>
      <div className='modal-body'>
        <div className='container-fluid' style={{padding: 0}}>
          <div className='row'>
            <div className='mb-1'>
              <h5>Details</h5>
            </div>
          </div>

          <div className='row'>
            <div className='col mb-2'>
              <label
                htmlFor='email'
                className='form-label'
              >
                Email
              </label>
              <div className='input-group has-validation'>
                <input
                  type='email'
                  className='form-control'
                  name='email'
                  id='email'
                  aria-describedby='inputGroupPrepend'
                  required
                  autoComplete='off'
                  value={this.props.email || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>
          <div className="row">
            <div className='col mb-2'>
              <label htmlFor='role' className='form-label'>
                Role
              </label>
              <select
                className='form-select'
                aria-label='Default select example'
                id='role'
                name='role'
                value={this.props.role || ''}
                onChange={this.props.onChange}
              >
                <option value='DISPATCHER'>Dispatcher</option>
                <option value='WAREHOUSE_MANAGER'>Warehouse manager</option>
                <option value='SHOP_MANAGER'>Shop manager</option>
                <option value='DIRECTOR'>Director</option>
              </select>
            </div>
          </div>
          {this.props.role !== 'DIRECTOR' && <div className='row'>
            <div className='col mb-2 mt-2'>
              <Autocomplete
                disablePortal
                required
                id='combo-box-demo'
                value={this.props.location}
                onChange={(event, newValue) => {
                  this.props.onLocationChange(newValue);
                }}
                options={this.props.locationIds}
                renderInput={(params) => <TextField {...params} required={true} label='Location' />}
              />
            </div>
          </div>}
          <div className='row'>
            <div className='col-6 mb-2'>
              <label
                htmlFor='name'
                className='form-label'
              >
                Name
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='name'
                  id='name'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  value={this.props.name || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
            <div className='col mb-2'>
              <label
                htmlFor='surname'
                className='form-label'
              >
                Surname
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='surname'
                  id='surname'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  value={this.props.surname || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>

          <div className='row'>
            <div className='col-6 mb-2'>
              <label
                htmlFor='login'
                className='form-label'
              >
                Login
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='login'
                  id='login'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  value={this.props.login || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
            <div className='col mb-2'>
              <label
                htmlFor='birthday'
                className='form-label'
              >
                Birthday
              </label>
              <div className='input-group has-validation'>
                <input
                  type='date'
                  className='form-control'
                  name='birthday'
                  id='birthday'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  value={this.props.birthday || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>

          <div className='row'>
            <div className='col-6 mt-1 mb-1'>
              <h5>Address</h5>
            </div>
          </div>

          <div className='row'>
            <div className='col-6 mb-2'>
              <label
                htmlFor='stateCode'
                className='form-label'
              >
                State code
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='stateCode'
                  id='stateCode'
                  aria-describedby='inputGroupPrepend'
                  required
                  pattern='.{2}'
                  autoComplete='off'
                  value={this.props.stateCode || ''}
                  onChange={this.props.onAddressChange}
                />
                <div className='invalid-feedback'>
                  Input should contain two symbols of the USA state code
                </div>
              </div>
            </div>
            <div className='col mb-2'>
              <label
                htmlFor='city'
                className='form-label'
              >
                City
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='city'
                  id='city'
                  aria-describedby='inputGroupPrepend'
                  required
                  autoComplete='off'
                  value={this.props.city || ''}
                  onChange={this.props.onAddressChange}
                />
              </div>
            </div>
          </div>
          <div className='row'>
            <div className='col-6 mb-2'>
              <label
                htmlFor='firstLine'
                className='form-label'
              >
                Address line 1
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='firstLine'
                  id='firstLine'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  value={this.props.firstLine || ''}
                  onChange={this.props.onAddressChange}
                />
              </div>
            </div>
            <div className='col mb-2'>
              <label
                htmlFor='secondLine'
                className='form-label'
              >
                Address line 2
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='secondLine'
                  id='secondLine'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  value={this.props.secondLine || ''}
                  onChange={this.props.onAddressChange}
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className='modal-footer'>
        <button
          type='button'
          className='btn btn-secondary'
          data-bs-dismiss='modal'
        >
          Close
        </button>
        <button type='submit' className='btn btn-primary'>
          Add
        </button>
      </div>
    </div>;
  }
}