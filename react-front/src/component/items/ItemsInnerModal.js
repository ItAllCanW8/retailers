import React, { Component } from 'react';

class ItemsInnerModal extends Component {
  render() {
    return <div className='modal-content'>
      <div className='modal-header'>
        <h5 className='modal-title' id='exampleModalLabel'>
          Add item
        </h5>
        <button
          type='button'
          className='btn-close'
          data-bs-dismiss='modal'
          aria-label='Close'
        />
      </div>
      <div className='modal-body'>
        <div className='container-fluid' style={{ padding: 0 }}>
          <div className='row'>
            <div className='col mb-2'>
              <label
                htmlFor='upc'
                className='form-label'
              >
                UPC
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='upc'
                  id='upc'
                  aria-describedby='inputGroupPrepend'
                  required
                  autoComplete='off'
                  value={this.props.upc}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>
          <div className='row'>
            <div className='col mb-2'>
              <label htmlFor='label' className='form-label'>
                Label
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='label'
                  id='label'
                  aria-describedby='inputGroupPrepend'
                  required
                  autoComplete='off'
                  value={this.props.label}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>
          <div className='row'>
            <div className='col mb-2'>
              <label
                htmlFor='units'
                className='form-label'
              >
                Units
              </label>
              <div className='input-group has-validation'>
                <input
                  type='number'
                  className='form-control'
                  name='units'
                  id='units'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  min='0'
                  value={this.props.units}
                  onChange={this.props.onChange}
                />
                <div className='invalid-feedback'>
                  Input should contain a positive integer number
                </div>
              </div>
            </div>
          </div>

          <div className='row'>
            <div className='col mb-2'>
              <label
                htmlFor='category'
                className='form-label'
              >
                Category
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='category'
                  id='category'
                  aria-describedby='inputGroupPrepend'
                  autoComplete='off'
                  required
                  value={this.props.category}
                  onChange={this.props.onCategoryChange}
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

export default ItemsInnerModal;