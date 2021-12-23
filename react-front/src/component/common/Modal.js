import React, { Component } from 'react';
import { Form } from './Form';

class Modal extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  handleSubmit = (event, submit) => {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      submit();
    } else {
      this.setState({ isFormValidated: false });
    }
  };

  render() {
    return (
      <div
        className='modal fade'
        id='customerModal'
        tabIndex='-1'
        aria-labelledby='exampleModalLabel'
        aria-hidden='true'
        ref={this.props.innerRef}
      >
        <div className='modal-dialog'>
          <Form onSubmit={this.props.submit}
                children={this.props.children} />
        </div>
      </div>
    );
  }
}

export default React.forwardRef((props, ref) => (
  <Modal innerRef={ref} {...props} />
));