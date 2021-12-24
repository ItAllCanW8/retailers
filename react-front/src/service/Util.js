import * as bootstrap from 'bootstrap';
import AuthService from './AuthService';

class Util {
  openModal = (modalRef) => {
    new bootstrap.Modal(modalRef.current).show();
  };

  showPositiveToast = (component, response, toastRef) => {
    component.setState({
      toastType: 'success',
      message: response.data.message
    });
    new bootstrap.Toast(toastRef.current).show();
  }

  showNegativeToast = (component, error, toastRef) => {
    if (error.response.data.message === '' || !error.response.data.message) {
      error.response.data.message = 'Incorrect input!'
    }
    component.setState({
      toastType: 'error',
      message: error.response.data.message
    });
    new bootstrap.Toast(toastRef.current).show();
  }

  redirectIfDoesntHaveRole = (component, role) => {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser || !currentUser.role.includes(role)) {
      component.setState({ redirect: '/' });
    }
  }

  handleChange = (component, event) => {
    let name = event.target.name;
    let value = event.target.value;
    let optionalIntValue = parseInt(value);
    if (!isNaN(optionalIntValue) && !value.includes('-')) {
      value = optionalIntValue;
    }
    component.setState({
      [name]: value
    });
  }

  handleAddressChange = (component, event) => {
    const target = event.target;
    component.setState({
      address: {
        ...component.state.address,
        [target.name]: target.value
      }
    });
  };

  handleLocationChange = (component, value) => {
    component.setState({
      location: {
        identifier: value
      }
    });
  };
}

export default new Util();