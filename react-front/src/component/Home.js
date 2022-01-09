import React, { Component } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: '',
    };
  }

  componentDidMount() {
    document.title = "Home";
    axios.get('/').then((response) => {
      this.setState({
        content: response.data,
      });
    });
  }


  render() {
    return (
      <div className="container mh-100">
        <div className="row text-center">
          <div className="col">
            <h3>Java Lab Retailers.</h3>
            <h3 className="mt-5 mb-2">Please, log in to the system.</h3>
            <Link to={'/login'} className="nav-link">
              Login
            </Link>
          </div>
        </div>
      </div>
    );
  }
}

export default Home;
