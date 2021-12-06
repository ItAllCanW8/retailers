import React, { Component } from 'react';
import axios from 'axios';

class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: '',
    };
  }

  componentDidMount() {
    axios.get('all').then((response) => {
      this.setState({
        content: response.data,
      });
    });
  }

  render() {
    return (
      <div>
        <header>
          <h3>{this.state.content}</h3>
        </header>
      </div>
    );
  }
}

export default Home;
