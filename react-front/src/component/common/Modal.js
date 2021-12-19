import React, { Component } from 'react';

class Modal extends Component {
  constructor(props) {
    super(props);
    this.state = {

    }
  }

  handleSubmit = (event, submit) => {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      submit();
    } else {
      this.setState({ isFormValidated: false });
    }
  }

  render() {
    return (
      <div
        className="modal fade"
        id="customerModal"
        tabIndex="-1"
        aria-labelledby="exampleModalLabel"
        aria-hidden="true"
        ref={this.props.innerRef}
      >
        <div className="modal-dialog">
          <form
            className={
              this.state.isFormValidated || this.state.isFormValidated === undefined
                ? 'row g-3 needs-validation'
                : 'row g-3 needs-validation was-validated'
            }
            noValidate
            onSubmit={() => this.handleSubmit(window.event, this.props.submit || function(){})}
          >
            {this.props.children}
          </form>
        </div>
      </div>
    );
  }
}

export default React.forwardRef((props, ref) => (
  <Modal innerRef={ref} {...props} />
));