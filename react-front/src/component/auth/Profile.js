import React, { Component, createRef } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import Util from '../../service/Util';
import Toast from '../common/Toast';
import AuthService from '../../service/AuthService';

export default class Profile extends Component {
  constructor(props) {
    super(props);
    this.toastRef = createRef();
    this.state = {
      user: {
        email: '',
        login: '',
        role: {
          role: ''
        },
        name: '',
        surname: '',
        birthday: '',
        address: {
          stateCode: '',
          city: '',
          firstLine: '',
          secondLine: ''
        },
        customer: {
          name: ''
        },
        location: {
          identifier: ''
        }
      },
      currentPassword: '',
      newPassword: ''
    };
  }

  componentDidMount() {
    document.title = "Profile";
    const currentUser = AuthService.getCurrentUser();
    if (!currentUser) {
      this.setState({ redirect: '/' });
    }
    this.updateProfile();
  }

  updateProfile = () => {
    axios.get('/profile').then(
      response => this.setState({ user: response.data }, () => {
        console.log(response.data);
      })
    );
  };

  handlePasswordChange = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    this.setState({
      ...this.state,
      [name]: value
    });
    console.log(this.state);
  }

  handleAddressChange = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    this.setState({
      ...this.state,
      user: {
        ...this.state.user,
        address: {
          ...this.state.user.address,
          [name]: value
        }
      }
    });
    console.log(this.state);
  }

  handleUserChange = (event) => {
    let name = event.target.name;
    let value = event.target.value;
    this.setState({
      user: {
        ...this.state.user,
        [name]: value
      }
    });
    console.log(this.state);
  };

  submit = () => {
    axios.put('profile', this.state, {
      headers: {
        'Content-Type': 'application/json',
      }
    } ).then(
      response => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateProfile();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
  };

  render() {
    if (!AuthService.currentUserHasRole('any')) {
      return <Redirect to={"/"} />;
    }

    const user = this.state.user;

    return (
      <div className='container mt-2'>
        <Toast
          toastType={this.state.toastType}
          message={this.state.message}
          ref={this.toastRef}
        />
        <div className='row justify-content-center mb-2'>
          <div className='col text-center'>
            <h2>Profile</h2>
          </div>
        </div>
        <form className='needs-validation' noValidate>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='email' className='form-label col-md-2 col-form-label'>
              Email
            </label>
            <div className='col-md-4'>
              <input type='text' id='email' name='email' className='form-control' defaultValue={user.email} readOnly />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='login' className='form-label col-md-2 col-form-label'>
              Login
            </label>
            <div className='col-md-4'>
              <input type='text' id='login' name='login' className='form-control' defaultValue={user.login} readOnly />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='role' className='form-label col-md-2 col-form-label'>
              Role
            </label>
            <div className='col-md-4'>
              <input type='text' id='role' name='role' className='form-control' defaultValue={user.role.role}
                     readOnly />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='name' className='form-label col-md-2 col-form-label'>
              Name
            </label>
            <div className='col-md-4'>
              <input type='text' id='name' name='name' className='form-control' onChange={this.handleUserChange}
                     value={user.name} />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='surname' className='form-label col-md-2 col-form-label'>
              Surname
            </label>
            <div className='col-md-4'>
              <input type='text' id='surname' name='surname' className='form-control' onChange={this.handleUserChange}
                     value={user.surname} />
            </div>
          </div>
          <div className='row mb-3 justify-content-center'>
            <label htmlFor='birthday' className='form-label col-md-2 col-form-label'>
              Birthday
            </label>
            <div className='col-md-4'>
              <input type='date' id='birthday' name='birthday' className='form-control' onChange={this.handleUserChange}
                     value={user.birthday} />
            </div>
          </div>
          <div className='row justify-content-center mb-1'>
            <div className='col text-center'>
              <h4>Password</h4>
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='currentPassword' className='form-label col-md-2 col-form-label'>
              Current password
            </label>
            <div className='col-md-4'>
              <input type='password' id='currentPassword' name='currentPassword' className='form-control' onChange={this.handlePasswordChange}
                     value={this.state.currentPassword} />
            </div>
          </div>
          <div className='row mb-3 justify-content-center'>
            <label htmlFor='newPassword' className='form-label col-md-2 col-form-label'>
              New password
            </label>
            <div className='col-md-4'>
              <input type='password' id='newPassword' name='newPassword' className='form-control' onChange={this.handlePasswordChange}
                     value={this.state.newPassword} />
            </div>
          </div>
          <div className='row justify-content-center mb-1'>
            <div className='col text-center'>
              <h4>Address</h4>
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='stateCode' className='form-label col-md-2 col-form-label'>
              State code
            </label>
            <div className='col-md-4'>
              <input type='text' id='stateCode' name='stateCode' className='form-control'
                     onChange={this.handleAddressChange}
                     value={user.address && user.address.stateCode} />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='city' className='form-label col-md-2 col-form-label'>
              City
            </label>
            <div className='col-md-4'>
              <input type='text' id='city' name='city' className='form-control'
                     onChange={this.handleAddressChange}
                     value={user.address && user.address.city} />
            </div>
          </div>
          <div className='row mb-2 justify-content-center'>
            <label htmlFor='firstLine' className='form-label col-md-2 col-form-label'>
              Address line 1
            </label>
            <div className='col-md-4'>
              <input type='text' id='firstLine' name='firstLine' className='form-control'
                     onChange={this.handleAddressChange}
                     value={user.address && user.address.firstLine} />
            </div>
          </div>
          <div className='row mb-3 justify-content-center'>
            <label htmlFor='secondLine' className='form-label col-md-2 col-form-label'>
              Address line 2
            </label>
            <div className='col-md-4'>
              <input type='text' id='secondLine' name='secondLine' className='form-control'
                     onChange={this.handleAddressChange}
                     value={user.address && user.address.secondLine} />
            </div>
          </div>
          <div className='row mb-3 justify-content-center'>
            <div className='col-md-6'>
              <button
                type='button'
                className='btn btn-primary me-3'
                onClick={this.submit}
              >
                Save
              </button>
            </div>
          </div>
        </form>
      </div>
    );
  }
}
