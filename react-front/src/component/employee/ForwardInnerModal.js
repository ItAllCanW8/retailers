import React, { Component } from 'react';
import { Autocomplete, TextField } from '@mui/material';

class ForwardInnerModal extends Component {
  render() {
    return (
      <div className='modal-content'>
        <div className='modal-header'>
          <h5 className='modal-title ps-2' id='exampleModalToggleLabel'>Forward application</h5>
          <button type='button' className='btn-close' data-bs-dismiss='modal' aria-label='Close' />
        </div>
        <div className='modal-body'>
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
        <div className='modal-footer'>
          <button type='submit' className='btn btn-primary'>
            Forward
          </button>
        </div>
      </div>
    );
  }
}

export default ForwardInnerModal;