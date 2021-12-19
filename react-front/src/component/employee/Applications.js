import React, { Component, createRef } from 'react';
import Util from '../../service/Util';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';
import Modal from '../common/Modal';
import ApplicationsInnerModal from './ApplicationsInnerModal';
import NoAvailableSpaceInnerModal from './NoAvailableSpaceInnerModal';
import ManagerContactsInnerModal from './ManagerContactsInnerModal';
import ForwardInnerModal from './ForwardInnerModal';

class Applications extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.managerContactsModalRef = createRef();
    this.noAvailableSpaceModalRef = createRef();
    this.forwardModalRef = createRef();
    this.state = {
      location: {
        availableAmount: 0,
        totalAmount: 0
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
    Util.redirectIfDoesntHaveRole(this, 'DISPATCHER');
    axios.get('locations-except-current').then(
      (response) => {
        this.setState({
          locationIds: response.data.map(location => location.identifier)
        });
      }
    );
    axios.get('current-location').then(
      (response) => {
        this.setState({
          currentLocationIdentifier: response.data.identifier
        });
      }
    );
    axios.get('managers').then(
      (response) => {
        this.setState({
          managers: response.data
        });
      }
    );
    this.updateApplications();
  }

  updateApplications = () => {
    axios.get('admin/current-location').then(
      (response) => {
        this.setState({
          location: response.data
        });
      }
    );
    axios.get('applications').then(
      (response) => {
        this.setState({
          applications: response.data
        });
      }
    );
  };

  submit = () => {
    this.modalRef.current.click();
    axios.post('/applications', this.state).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateApplications();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
  };

  handleLocationSelection = (event, id) => {
    if (event.target.checked) {
      this.setState({
        ids: [...this.state.ids, id]
      });
    } else {
      let array = this.state.ids;
      let index = array.indexOf(id);
      if (index !== -1) {
        array.splice(index, 1);
      }
      this.setState({
        ids: array
      });
    }

  };

  deleteLocations = () => {
    axios.delete('admin/locations', {
      data: this.state.ids
    })
    .then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.setState({ ids: [] });
        this.updateLocations();
      }
    );
  };

  changeItem = (event, index) => {
    let name = event.target.name;
    let value = event.target.value;
    let optionalIntValue = parseInt(value);
    if (!isNaN(optionalIntValue) && !value.includes('-')) {
      value = optionalIntValue;
    }
    const newItems = this.state.items;
    newItems[index][name] = value;
    this.setState({
      items: newItems
    }, () => console.log(this.state.items));
  };

  addItem = () => {
    const newItems = this.state.items;
    newItems.push({ upc: '', amount: '', coast: null });
    console.log(newItems);
    this.setState({
      ...this.state,
      items: newItems
    });
  };

  acceptApplication = (id) => {
    axios.put('/application/' + id + '/accept').then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateApplications();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
        this.setState({
          selectedApplicationId: id
        })
        Util.openModal(this.noAvailableSpaceModalRef);
      }
    );
  };

  handleAutocompleteChange = (value) => {
    this.setState({
      forwardLocation: value
    });
  }

  submitForwarding = (id) => {
    this.forwardModalRef.current.click();
    this.noAvailableSpaceModalRef.current.click();
    axios.put("application/" + id + "/forward", this.state.forwardLocation).then(
      (response) => {
      Util.showPositiveToast(this, response, this.toastRef);
      this.updateApplications();
    },
    (error) => {
      Util.showNegativeToast(this, error, this.toastRef);
      Util.openModal(this.noAvailableSpaceModalRef);
    }
    )
  };

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    return (
      <div>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
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
            <h4>Space available: {this.state.location.availableAmount}/{this.state.location.totalAmount}</h4>
          </div>
        </div>
        <Modal ref={this.noAvailableSpaceModalRef}>
          <NoAvailableSpaceInnerModal
            managerContactsModalRef={this.managerContactsModalRef}
            forwardModalRef={this.forwardModalRef}
          />
        </Modal>
        <Modal ref={this.managerContactsModalRef}>
          <ManagerContactsInnerModal managers={this.state.managers}/>
        </Modal>
        <Modal ref={this.forwardModalRef} submit={() => this.submitForwarding(this.state.selectedApplicationId)}>
          <ForwardInnerModal
            onLocationChange={(value) => this.handleAutocompleteChange(value)}
            forwardLocation={this.state.forwardLocation}
            locationIds={this.state.locationIds}
          />
        </Modal>
        <Modal
          ref={this.modalRef}
          submit={this.submit}
        >
          <ApplicationsInnerModal applicationNumber={this.state.applicationNumber} location={this.state.location}
                                  locationIds={this.state.locationIds}
                                  status={this.state.status} items={this.state.items}
                                  addItem={this.addItem}
                                  onItemChange={this.changeItem}
                                  onLocationChange={(value) => Util.handleLocationChange(this, value)}
                                  onChange={() => Util.handleChange(this, window.event)}
          />
        </Modal>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>Number</th>
            <th scope='col'>Source location</th>
            <th scope='col'>Destination location</th>
            <th scope='col'>Last updated time</th>
            <th scope='col'>Last updated by</th>
            <th scope='col'>Status</th>
          </tr>
          </thead>
          <tbody>
          {this.state.applications && this.state.applications.map((application) => (
            <tr key={application.id}>
              <th scope='row'>
                {application.status === 'STARTED_PROCESSING'
                && application.destLocation.identifier === this.state.currentLocationIdentifier
                && (
                  <button className='btn btn-primary'
                          onClick={() => this.acceptApplication(application.id)}
                  >
                    Accept
                  </button>
                )}
              </th>
              <td>{application.applicationNumber}</td>
              <td>{application.srcLocation && application.srcLocation.identifier}</td>
              <td>{application.destLocation.identifier}</td>
              <td>{application.lastUpdDateTime}</td>
              <td>{application.lastUpdBy.name}</td>
              <td>{application.status}</td>

            </tr>
          ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Applications;