import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

class Users extends Component {
  constructor(props) {
    super(props);
    this.state = {
      redirect: null
    }
  }


  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect} />;
    }
    return (
      <div>

      </div>
    );
  }
}

export default Users;