import React, { Component } from 'react';
import axios from 'axios';
import AuthService from '../../service/AuthService';
import { Redirect } from 'react-router-dom';
import LocationItems from '../warehouse/LocationItems';

class ShopItems extends Component {
  constructor(props) {
    super(props);
    this.state = {
      locationItems: [],
      currentLocation: {
        location: {}
      }
    };
  }

  componentDidMount() {
    document.title = "Shop";
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
                amount: '',
                cost: item.cost
              };
            })
          }
        });
      }
    );
  }

  render() {
    if (!AuthService.currentUserHasRole('ROLE_SHOP_MANAGER')) {
      return <Redirect to={"/profile"} />;
    }
    return (
      <div>
        <div className='row align-items-center mb-3'>
          <div className='col align-items-center'>
            <h4>
              Space available: {this.state.currentLocation.availableAmount}
              /
              {this.state.currentLocation.location.totalCapacity}
            </h4>
          </div>
        </div>
        <LocationItems
          locationItems={this.state.locationItems}
        />
      </div>
    );
  }
}

export default ShopItems;