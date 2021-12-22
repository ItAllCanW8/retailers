import React, { Component } from 'react';

export class CustomerInnerModal extends Component {
  render() {
    return <div className='modal-content'>
      <div className='modal-header'>
        <h5 className='modal-title' id='exampleModalLabel'>
          Add customer
        </h5>
        <button
          type='button'
          className='btn-close'
          data-bs-dismiss='modal'
          aria-label='Close'
        />
      </div>
      <div className='modal-body'>
        <div className='col mb-2'>
          <label
            htmlFor='validationCustomUsername'
            className='form-label'
          >
            Username
          </label>
          <div className='input-group has-validation'>
            <input
              type='text'
              className='form-control'
              name='name'
              id='name'
              aria-describedby='inputGroupPrepend'
              required
              autoComplete='off'
              value={this.props.name || ''}
              onChange={this.props.onChange}
            />
            <div className='invalid-feedback'>
              Please choose a username.
            </div>
          </div>
        </div>
        <div className='col'>
          <label htmlFor='validationCustom03' className='form-label'>
            Email
          </label>
          <input
            type='email'
            className='form-control'
            name='email'
            id='email'
            required
            autoComplete='off'
            value={this.props.email || ''}
            onChange={this.props.onChange}
          />
          <div className='invalid-feedback'>
            Please enter a valid email
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