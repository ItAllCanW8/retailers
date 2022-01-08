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
    if (!isNaN(value) && !isNaN(parseFloat(value)) && isFinite(value)) {
      value = +value;
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

  handleCategoryTaxChange = (component, value, index) => {
    if (!isNaN(value) && !isNaN(parseFloat(value)) && isFinite(value)) {
      value = +value;
    }
    const newArray = component.state.categories;
    newArray[index].categoryTax = value;
    component.setState({
      categories: newArray
    });
  };

  handleRentalTaxChange = (component, value, index) => {
    if (!isNaN(value) && !isNaN(parseFloat(value)) && isFinite(value)) {
      value = +value;
    }
    const newArray = component.state.locations;
    newArray[index].rentalTaxRate = value;
    component.setState({
      locations: newArray
    });
  };

  toPage = (component, callback, page) => {
    component.setState({
      params: {
        ...component.state.params,
        page: page
      }
    }, callback);
  };
}

export default new Util();