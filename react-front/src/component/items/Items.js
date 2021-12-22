import React, { Component, createRef } from 'react';
import Util from '../../service/Util';
import axios from 'axios';
import { Redirect } from 'react-router-dom';
import Toast from '../common/Toast';
import Modal from '../common/Modal';
import ItemsInnerModal from './ItemsInnerModal';

class Items extends Component {
  constructor(props) {
    super(props);
    this.modalRef = createRef();
    this.toastRef = createRef();
    this.state = {
      items: [],
      ids: [],
      upc: '',
      label: '',
      units: null,
      category: {
        name: ''
      }
    };
  }


  componentDidMount() {
    Util.redirectIfDoesntHaveRole(this,'item:post');

    this.updateItems();
  }

  updateItems = () => {
    axios.get('/items').then(
      (response) => {
        this.setState({
          items: response.data
        });
      }
    );
  };

  submit = () => {
    this.modalRef.current.click();
    axios.post('/items', this.state).then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.updateItems();
      },
      (error) => {
        Util.showNegativeToast(this, error, this.toastRef);
      }
    );
  };

  handleItemSelection = (event, id) => {
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

  handleCategoryChange = (component, event) => {
    const target = event.target;
    component.setState({
      category: {
        name: target.value
      }
    });
  };

  deleteItems = () => {
    axios.delete('/items/', {
      data: this.state.ids
    })
    .then(
      (response) => {
        Util.showPositiveToast(this, response, this.toastRef);
        this.setState({ ids: [] });
        this.updateItems();
      }
    );
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
          <div className='col align-items-center'>
            <button
              type='button'
              className='btn btn-primary me-3'
              onClick={() => Util.openModal(this.modalRef)}
            >
              Add
            </button>
            <button
              type='button'
              className='btn btn-primary btn-danger me-3'
              disabled={this.state.ids.length === 0}
              onClick={this.deleteItems}
            >
              Remove
            </button>
          </div>
        </div>
        <Modal
          ref={this.modalRef}
          submit={this.submit}
        >

          <ItemsInnerModal upc={this.state.upc} label={this.state.label}
                           units={this.state.units} category={this.state.category.name}
                           onChange={() => Util.handleChange(this, window.event)}
                           onCategoryChange={() => this.handleCategoryChange(this, window.event)}
          />
        </Modal>
        <table className='table table-striped'>
          <thead>
          <tr>
            <th scope='col' />
            <th scope='col'>UPC</th>
            <th scope='col'>Label</th>
            <th scope='col'>Category</th>
            <th scope='col'>Units</th>
          </tr>
          </thead>
          <tbody>
          {this.state.items && this.state.items.map((item) => (
            <tr key={item.id}>
              <th scope='row'>
                <input className='form-check-input' type='checkbox' value={item.id}
                       onChange={() => this.handleItemSelection(window.event, item.id)} />
              </th>
              <td>{item.upc}</td>
              <td>{item.label}</td>
              <td>{item.category.name}</td>
              <td>{item.units}</td>
            </tr>
          ))}
          </tbody>
        </table>
      </div>
    );
  }
}

export default Items;