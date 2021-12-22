import React, { Component } from 'react';
import { Autocomplete, TextField } from '@mui/material';

class ForwardInnerModal extends Component {
  render() {
    return (
      <div className='modal-content'>
        <div className='modal-header'>
          <h5 className='modal-title ps-2' id='exampleModalToggleLabel'>Dispatch application</h5>
          <button type='button' className='btn-close' data-bs-dismiss='modal' aria-label='Close' />
        </div>
        <div className='modal-body'>
          <div className='row'>
            <div className='col mb-2'>
              {this.props.applicationNameShown && <div className='input-group has-validation'>
                <label htmlFor='applicationNumber' className='form-label'>Application number</label>
                <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control mb-3'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  id='applicationNumber'
                  required
                  value={this.props.forwardLocation.applicationNumber}
                  onChange={this.props.onApplicationNumberChange}
                />
                </div>
              </div>}
            </div>
          </div>
          <div className='row'>
            <div className='col mb-2'>
              <Autocomplete
                disablePortal
                required
                id='combo-box-demo'
                value={this.props.forwardLocation}
                onChange={(event, newValue) => {
                  this.props.onLocationChange(newValue);
                }}
                options={this.props.locationIds}
                renderInput={(params) => <TextField {...params} required={true} label='Location' />}
              />
            </div>
          </div>
        </div>
        <div className='modal-footer'>
          <button type='submit' className='btn btn-primary'>
            Submit
          </button>
        </div>
      </div>
    );
  }
}

export default ForwardInnerModal;