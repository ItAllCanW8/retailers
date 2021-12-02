import React, {Component} from 'react';

class Customers extends Component {
  render() {
    return (
      <div className="list-group">
        {this.props.content && this.props.content.map((content, index) =>
          <a key={index} className="list-group-item list-group-item-action" aria-current="true">
            <div className="d-flex w-100 justify-content-between">
              <h5 className="mb-1">{content.customer.name}</h5>
              <div className="form-check form-switch">
                <input className="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" checked/>
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