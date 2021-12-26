import React, { Component } from 'react';
import * as PropTypes from 'prop-types';

export class Form extends Component {
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
    return <form
      className={
        this.state.isFormValidated || this.state.isFormValidated === undefined
          ? 'row g-3 needs-validation'
          : 'row g-3 needs-validation was-validated'
      }
      noValidate
      onSubmit={() => this.handleSubmit(window.event, this.props.onSubmit || function() {})}
    >
      {this.props.children}
    </form>;
  }
}

Form.propTypes = {
  formValidated: PropTypes.any,
  onSubmit: PropTypes.func,
  children: PropTypes.any
};