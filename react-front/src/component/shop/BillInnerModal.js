import React, { Component } from 'react';

class BillInnerModal extends Component {
  render() {
    let index;
    return <div className='modal-content'>
      <div className='modal-header'>
        <h5 className='modal-title' id='exampleModalLabel'>
          Add bill
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
                Bill number
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
                  value={this.props.number}
                  onChange={this.props.onNumberChange}
                />
              </div>
            </div>
          </div>
          {this.props.items && this.props.items.map((item, index, arr) => (
            <div className='row' key={index}>
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
                    value={item.upc}
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
                    type='number'
                    className='form-control'
                    name={'amount'}
                    id={'amount' + index}
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    min='0'
                    value={item.amount}
                    onChange={() => this.props.onItemChange(window.event, index)}
                  />
                </div>
              </div>
              <div className='col mb-2'>
                {index === 0 && (
                  <label htmlFor={'price' + index} className='form-label'>Price</label>
                )}
                <div className='input-group has-validation'>
                  <input
                    type='number'
                    className='form-control'
                    name={'price'}
                    id={'price' + index}
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    min='0'
                    step='0.01'
                    value={item.price}
                    onChange={() => this.props.onItemChange(window.event, index)}
                  />
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <div className='modal-footer' style={{display: 'block'}}>
        <div className='d-flex'>
          <div className='p-1'>
            <button className='btn btn-light' onClick={this.props.addItem}>+</button>
          </div>
          <div className='me-auto p-1'>
            <button className='btn btn-light' onClick={this.props.removeItem}>-</button>
          </div>
          <div className='p-1'>
            <button
              type='button'
              className='btn btn-secondary'
              data-bs-dismiss='modal'
            >
              Close
            </button>
          </div>
          <div className='p-1'>
            <button type='submit' className='btn btn-primary'>
              Add
            </button>
          </div>
        </div>

      </div>
    </div>;
  }
}

export default BillInnerModal;