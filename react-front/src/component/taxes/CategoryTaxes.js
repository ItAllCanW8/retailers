import React, { Component, createRef } from 'react';
import axios from 'axios';
import Util from '../../service/Util';
import AuthService from '../../service/AuthService';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';

class CategoryTaxes extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      categories: [{
        category: {
          name: ''
        },
        categoryTax: 0
      }]
    };
  }

  componentDidMount() {
    this.updateTaxes();
  }

  updateTaxes = () => {
    axios.get('/categories').then(
      (response) => {
        this.setState({
          categories: response.data
        });
      }
    );
  };

  submit = () => {
    axios.put("/taxes/category", this.state.categories).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateTaxes();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
  }

  render() {
    if (!AuthService.currentUserHasRole('ROLE_DIRECTOR')) {
      return <Redirect to={"/"} />;
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
              onClick={this.submit}
            >
              Save
            </button>
          </div>
        </div>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>Category</th>
            <th scope='col'>Tax, %</th>
          </tr>
          </thead>
          <tbody>
          {this.state.categories && this.state.categories.map((category, index) => {
            return (
              <tr key={category.id}>
                <th scope='row'>

                </th>
                <td>{category.category.name}</td>
                <td>
                  <input
                    type='number'
                    className='form-control'
                    aria-describedby='inputGroupPrepend'
                    autoComplete='off'
                    required
                    min='0'
                    step='0.01'
                    style={{ width: 130 }}
                    value={category.categoryTax}
                    onChange={() => Util.handleCategoryTaxChange(this, window.event.target.value, index)}
                  />
                </td>
              </tr>
            );
          })}
          </tbody>
        </table>
      </div>
    );
  }
}

export default CategoryTaxes;