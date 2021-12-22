import React, { Component, createRef } from 'react';
import Util from '../../service/Util';
import axios from 'axios';
import Toast from '../common/Toast';
import ForwardInnerModal from '../applications/ForwardInnerModal';
import Modal from '../common/Modal';

class Warehouse extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.managerContactsModalRef = createRef();
    this.noAvailableSpaceModalRef = createRef();
    this.forwardModalRef = createRef();
    this.state = {
      currentLocation: {
        location: {},
        availableAmount: 0
      },
      locationItems: [],
      dispatchRequest: {
        applicationNumber: '',
        destLocation: '',
        itemsToDispatch: [{
          upc: 0,
          amount: 0
        }]
      },
      currentLocationIdentifier: '',
      selectedApplicationId: '',
      forwardLocation: '',
      managers: [{}],
      locationIds: [],
      applications: [],
      applicationNumber: '',
      status: '',
      items: [{
        upc: '',
        amount: '',
        cost: null
      }],
      redirect: null
    };
  }

  componentDidMount() {
    Util.redirectIfDoesntHaveRole(this, 'application:get');

    axios.get('locations-except-current').then(
      (response) => {
        this.setState({
          locationIds: response.data.map(location => location.identifier)
        });
      }
    );

    this.updateLocationItems();
  }

  updateLocationItems = () => {
    axios.get('current-location').then(
      (response) => {
        this.setState({
          currentLocation: response.data
        });
      }
    );

    axios.get('current-location-items').then(
      (response) => {
        this.setState({
          ...this.state,
          locationItems: response.data,
          dispatchRequest: {
            ...this.state.dispatchRequest,
            itemsToDispatch: response.data.map(item => {
              return {
                upc: item.upc,
                amount: ''
              };
            })
          }
        });
      }
    );
  };

  dispatch = () => {
    axios.post("/dispatch-items", this.state.dispatchRequest).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateLocationItems();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
    this.forwardModalRef.current.click();
  }

  handleItemsToDispatchInput = (event, index) => {
    let value = event.target.value;
    let optionalIntValue = parseInt(value);
    if (!isNaN(optionalIntValue) && !value.includes('-')) {
      value = optionalIntValue;
    }
    const newItems = this.state.dispatchRequest.itemsToDispatch;
    newItems[index].amount = value;
    this.setState({
      dispatchRequest: {
        ...this.state.dispatchRequest,
        itemsToDispatch: newItems
      }
    });
  }

  handleApplicationNumberChange = (event) => {
    this.setState({
      dispatchRequest: {
        ...this.state.dispatchRequest,
        applicationNumber: event.target.value
      }
    });
  };

  handleAutocompleteChange = (value) => {
    this.setState({
      dispatchRequest: {
        ...this.state.dispatchRequest,
        destLocation: value
      }
    });
  };

  render() {
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <Modal ref={this.forwardModalRef} submit={this.dispatch}>
          <ForwardInnerModal
            applicationNameShown={true}
            onApplicationNumberChange={this.handleApplicationNumberChange}
            onLocationChange={(value) => this.handleAutocompleteChange(value)}
            forwardLocation={this.state.dispatchRequest.destLocation}
            locationIds={this.state.locationIds}
          />
        </Modal>
        <div className='row align-items-center mb-3'>
          <div className='col-auto align-items-center'>
            <button
              type='button'
              className='btn btn-primary me-3'
              onClick={() => Util.openModal(this.forwardModalRef)}
            >
              Dispatch
            </button>
          </div>
          <div className='col align-items-center'>
            <h4>
              Space available: {this.state.currentLocation.availableAmount}
              /
              {this.state.currentLocation.location.totalCapacity}
            </h4>
          </div>
        </div>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>Upc</th>
            <th scope='col'>Label</th>
            <th scope='col'>Amount to dispatch</th>
            <th scope='col'>Amount</th>
          </tr>
          </thead>
          <tbody>
          {this.state.locationItems && this.state.locationItems.map((item, index) => {
            let itemsToDispatch = this.state.dispatchRequest.itemsToDispatch[index];
            return (
              <tr key={item.id}>
                <th scope='row'>

                </th>
                <td>{item.upc}</td>
                <td>{item.label}</td>
                <td>
                  <input
                    type='text'
                    className='form-control'
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    pattern='[0-9]+'
                    style={{ width: 150 }}
                    value={itemsToDispatch && itemsToDispatch.amount}
                    onChange={() => this.handleItemsToDispatchInput(window.event, index)}
                  />
                </td>
                <td>{item.amount}</td>

              </tr>
            );
          })}
          </tbody>
        </table>
      </div>
    );
  }


}

export default Warehouse;