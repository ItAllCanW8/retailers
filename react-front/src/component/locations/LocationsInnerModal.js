import React, { Component } from 'react';
import StateCodes from '../common/StateCodes';

export class LocationsInnerModal extends Component {
    render() {
        return <div className='modal-content'>
            <div className='modal-header'>
                <h5 className='modal-title' id='exampleModalLabel'>
                    Add location
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
                        <div className='col-6 mb-2'>
                            <label
                                htmlFor='identifier'
                                className='form-label'
                            >
                                Identifier
                            </label>
                            <div className='input-group has-validation'>
                                <input
                                    type='text'
                                    className='form-control'
                                    name='identifier'
                                    id='identifier'
                                    aria-describedby='inputGroupPrepend'
                                    required
                                    autoComplete='off'
                                    value={this.props.identifier}
                                    onChange={this.props.onChange}
                                />
                            </div>
                        </div>
                        <div className='col mb-2'>
                            <label htmlFor='type' className='form-label'>
                                Type
                            </label>
                            <select
                                className='form-select'
                                aria-label='Default select example'
                                id='type'
                                name='type'
                                value={this.props.type}
                                onChange={this.props.onChange}
                            >
                                <option value='WAREHOUSE'>Warehouse</option>
                                <option value='OFFLINE_SHOP'>Offline shop</option>
                            </select>
                        </div>
                    </div>

                    <div className='row'>
                        <div className='col mb-2'>
                            <label
                                htmlFor='totalCapacity'
                                className='form-label'
                            >
                                Total capacity
                            </label>
                            <div className='input-group has-validation'>
                                <input
                                    type='number'
                                    className='form-control'
                                    name='totalCapacity'
                                    id='totalCapacity'
                                    aria-describedby='inputGroupPrepend'
                                    autoComplete='off'
                                    required
                                    min='0'
                                    value={this.props.totalCapacity}
                                    onChange={this.props.onChange}
                                />
                                <div className='invalid-feedback'>
                                    Input should contain a positive integer number
                                </div>
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
                            <StateCodes/>
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
                                    value={this.props.city}
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
                                    value={this.props.firstLine}
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
                                    value={this.props.secondLine}
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