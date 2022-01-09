import React, { Component, createRef } from 'react';
import axios from 'axios';
import Util from '../../service/Util';
import AuthService from '../../service/AuthService';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';
import Modal from '../common/Modal';
import BillInnerModal from './BillInnerModal';

class Bills extends Component {
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
      bills: [],
      sellRequest: {
        number: '',
        itemAssoc: [{
          item: {
            upc: '',
          },
          amount: ''
        }]
      },
      forwardLocation: '',
      locationIds: [],
      applications: [],
      applicationNumber: '',
      status: ''
    };
  }

  componentDidMount() {
    document.title = "Bills";
    this.updateBills();
  }

  updateBills = () => {
    axios.get('bills').then(
      (response) => {
        this.setState({
          bills: response.data
        });
      }
    );

    axios.get('current-location').then(
      (response) => {
        this.setState({
          currentLocation: response.data
        });
      }
    );

  };

  sell = () => {
    axios.post('/bills', this.state.sellRequest).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateBills();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
    this.modalRef.current.click();
  };

  handleNumberChange = (event) => {
    this.setState({
      sellRequest: {
        ...this.state.sellRequest,
        number: event.target.value
      }
    });
  };

  changeItem = (event, index) => {
    let name = event.target.name;
    let value = event.target.value;
    if (!isNaN(value) && !isNaN(parseFloat(value)) && isFinite(value)) {
      value = +value;
    }
    let newItems = this.state.sellRequest.itemAssoc;
    if (name === 'upc') {
      newItems[index].item.upc = value;
    } else {
      newItems[index][name] = value;
    }
    this.setState({
      sellRequest: {
        ...this.state.sellRequest,
        itemAssoc: newItems
      }
    });
  };

  addItem = () => {
    const newItems = this.state.sellRequest.itemAssoc;
    newItems.push({ item : { upc: '' }, amount: '', coast: null });
    this.setState({
      sellRequest: {
        ...this.state.sellRequest,
        itemAssoc: newItems
      }
    });
  };

  removeItem = () => {
    const newItems = this.state.sellRequest.itemAssoc;
    newItems.pop();
    this.setState({
      sellRequest: {
        ...this.state.sellRequest,
        itemAssoc: newItems
      }
    });
  };

  render() {
    if (!AuthService.currentUserHasRole('ROLE_SHOP_MANAGER')) {
      return <Redirect to={'/profile'} />;
    }
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <Modal ref={this.modalRef} submit={this.sell}>
          <BillInnerModal
            number={this.state.sellRequest.number}
            onNumberChange={this.handleNumberChange}
            items={this.state.sellRequest.itemAssoc}
            addItem={this.addItem}
            removeItem={this.removeItem}
            onItemChange={this.changeItem}
            locationIds={this.state.locationIds}
          />
        </Modal>
        <div className='row align-items-center mb-3'>
          <div className='col-auto align-items-center'>
            <button
              type='button'
              className='btn btn-primary me-3'
              onClick={() => Util.openModal(this.modalRef)}
            >
              Sell
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
            <th scope='col'>Number</th>
            <th scope='col'>Total item amount</th>
            <th scope='col'>Total item sum</th>
          </tr>
          </thead>
          <tbody>
          {this.state.bills && this.state.bills.map((bill, index) => {
            return (
              <tr key={bill.id}>
                <th scope='row'>
                </th>
                <td>{bill.number}</td>
                <td>{bill.totalItemAmount}</td>
                <td>{Number((bill.totalItemSum).toFixed(2))}</td>
              </tr>
            );
          })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Bills;