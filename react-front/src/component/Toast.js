import React, {Component} from 'react';

class Toast extends Component {
  render() {
    return (
      <div className="position-fixed bottom-0 end-0 p-3" style={{zIndex: 11}}>
        <div id="liveToast" className="toast" role="alert" aria-live="assertive" aria-atomic="true"
             ref={this.props.innerRef}
             style={this.props.toastType === 'error'
               ? {backgroundColor: "#f8d7da", color: "#8a2029"}
               : {backgroundColor: "#d1e7dd", color: "#0f5132"}}>
          <div className="d-flex">
            <div className="toast-body fs-6 fw-bold">
              {this.props.message}
            </div>
            <button type="button" className="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"/>
          </div>
        </div>
      </div>
    );
  }
}

export default React.forwardRef((props, ref) => <Toast
  innerRef={ref} {...props}
/>);