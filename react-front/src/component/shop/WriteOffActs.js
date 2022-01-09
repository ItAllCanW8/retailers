import React, { Component, createRef } from 'react';
import axios from 'axios';
import Util from '../../service/Util';
import AuthService from '../../service/AuthService';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';
import Modal from '../common/Modal';
import WriteOffActInnerModal from './WriteOffActInnerModal';

class WriteOffActs extends Component {
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
      writeOffAct: {
        identifier: '',
        writtenOffItems: [{
          item: {
            upc: ''
          },
          amount: '',
          reason: 'DAMAGED'
        }]
      }
    };
  }

  componentDidMount() {
    document.title = "Write off acts";
    this.updateWriteOffActs();
  }

  updateWriteOffActs = () => {
    axios.get('local-write-off-acts').then(
      (response) => {
        this.setState({
          writeOffActs: response.data
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
    axios.post('/write-off-acts', this.state.writeOffAct).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateWriteOffActs();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
    this.modalRef.current.click();
  };

  handleIdentifierChange = (event) => {
    this.setState({
      writeOffAct: {
        ...this.state.writeOffAct,
        identifier: event.target.value
      }
    });
  };

  changeItem = (event, index) => {
    let name = event.target.name;
    let value = event.target.value;
    if (!isNaN(value) && !isNaN(parseFloat(value)) && isFinite(value)) {
      value = +value;
    }
    let newItems = this.state.writeOffAct.writtenOffItems;
    if (name === 'upc') {
      newItems[index].item.upc = value;
    } else {
      newItems[index][name] = value;
    }
    this.setState({
      writeOffAct: {
        ...this.state.writeOffAct,
        writtenOffItems: newItems
      }
    });
  };

  addItem = () => {
    const newItems = this.state.writeOffAct.writtenOffItems;
    newItems.push({ item : { upc: '' }, amount: '', reason: '' });
    this.setState({
      writeOffAct: {
        ...this.state.writeOffAct,
        writtenOffItems: newItems
      }
    });
  };

  removeItem = () => {
    const newItems = this.state.writeOffAct.writtenOffItems;
    newItems.pop();
    this.setState({
      writeOffAct: {
        ...this.state.writeOffAct,
        writtenOffItems: newItems
      }
    });
  };

  render() {
    if (!AuthService.currentUserHasRole('ROLE_SHOP_MANAGER')) {
      return <Redirect to={'/'} />;
    }
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <Modal ref={this.modalRef} submit={this.sell}>
          <WriteOffActInnerModal
            identifier={this.state.writeOffAct.identifier}
            onIdentifierChange={this.handleIdentifierChange}
            writtenOffItems={this.state.writeOffAct.writtenOffItems}
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
              Add
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
            <th scope='col'>Identifier</th>
            <th scope='col'>Create time</th>
            <th scope='col'>Total item amount</th>
            <th scope='col'>Total item sum</th>
          </tr>
          </thead>
          <tbody>
          {this.state.writeOffActs && this.state.writeOffActs.map((writeOffACt, index) => {
            return (
              <tr key={writeOffACt.identifier}>
                <th scope='row'>
                </th>
                <td>{writeOffACt.identifier}</td>
                <td>{writeOffACt.dateTime}</td>
                <td>{writeOffACt.totalItemAmount}</td>
                <td>{writeOffACt.totalItemSum}</td>
              </tr>
            );
          })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default WriteOffActs;