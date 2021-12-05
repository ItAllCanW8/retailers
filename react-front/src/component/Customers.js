import React, {Component} from 'react';
import axios from "axios";
import authHeader from "../service/auth-header";

const API_URL = "http://localhost:8080/api/";

class Customers extends Component {
  constructor(props) {
    super(props);
    this.state = {
      statuses: null
    }
  }

  componentDidMount() {
    let map = new Map();
    if (this.props.content) {
      this.props.content.forEach(item => map.set(item.customer.id, item.customer.active));
      this.setState({statuses: map});
    }
  }

  changeCustomerStatus = (id) => {
    let map = this.state.statuses;
    map.set(id, !map.get(id));
    this.setState({statuses: map});
    axios.post(API_URL + "system-admin/" + id, {active: this.state.statuses.get(id)}, {headers: authHeader()});
  }

  render() {
    return (
      <div className="list-group">
        {this.props.content && this.props.content.map(content =>
          <a key={content.customer.id} className="list-group-item list-group-item-action" aria-current="true">
            <div className="d-flex w-100 justify-content-between">
              <h5 className="mb-1">{content.customer.name}</h5>
              <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" role="switch" name="status"
                       id="flexSwitchCheckChecked"
                       onChange={() => this.changeCustomerStatus(content.customer.id)} checked={
                  //content.customer.active
                  this.state && this.state.statuses && this.state.statuses.get(content.customer.id)
                }/>
                <label className="form-check-label" htmlFor="flexSwitchCheckChecked"/>
              </div>
            </div>
            <p className="mb-1">{content.email}</p>
            <small>Reg date: {content.customer.regDate}</small>
          </a>
        )}
      </div>
    );
  }
}

export default Customers;