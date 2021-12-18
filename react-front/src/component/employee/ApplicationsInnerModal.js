import React, { Component } from 'react';

class ApplicationsInnerModal extends Component {
  render() {
    return <div className='modal-content'>
      <div className='modal-header'>
        <h5 className='modal-title' id='exampleModalLabel'>
          Add application
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
                htmlFor='applicationNumber'
                className='form-label'
              >
                Application number
              </label>
              <div className='input-group has-validation'>
                <input
                  type='text'
                  className='form-control'
                  name='applicationNumber'
                  id='applicationNumber'
                  aria-describedby='inputGroupPrepend'
                  required
                  autoComplete='off'
                  value={this.props.applicationNumber || ''}
                  onChange={this.props.onChange}
                />
              </div>
            </div>
          </div>
          {this.props.items && this.props.items.map((item, index, arr) => (
            <div className='row' key={index}>
              {index === arr.length - 1 && (
                <div className='col-auto d-flex align-items-center'>
                  <button className='btn btn-light' onClick={this.props.addItem}>+</button>
                </div>
              )}

              <div className='col-4 mb-2'>
                {index === 0 && (
                  <label htmlFor={'upc' + index} className='form-label'>UPC</label>
                )}

                <div className='input-group has-validation'>
                  <input
                    type='text'
                    className='form-control'
                    name={'upc'}
                    id={'upc' + index}
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    value={item.upc || ''}
                    onChange={() => this.props.onItemChange(window.event, index)}
                  />
                </div>
              </div>
              <div className='col-4 mb-2'>
                {index === 0 && (
                  <label htmlFor={'amount' + index} className='form-label'>Amount</label>
                )}
                <div className='input-group has-validation'>
                  <input
                    type='text'
                    className='form-control'
                    name={'amount'}
                    id={'amount' + index}
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    pattern='[0-9]+'
                    value={item.amount || ''}
                    onChange={() => this.props.onItemChange(window.event, index)}
                  />
                </div>
              </div>
              <div className='col mb-2'>
                {index === 0 && (
                  <label htmlFor={'cost' + index} className='form-label'>Cost</label>
                )}
                <div className='input-group has-validation'>
                  <input
                    type='text'
                    className='form-control'
                    name={'cost'}
                    id={'cost' + index}
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    pattern='[0-9]+'
                    value={item.cost || ''}
                    onChange={() => this.props.onItemChange(window.event, index)}
                  />
                </div>
              </div>
            </div>
          ))}
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

export default ApplicationsInnerModal;