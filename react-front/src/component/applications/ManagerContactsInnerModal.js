import React, { Component } from 'react';

class ManagerContactsInnerModal extends Component {
  render() {
    return (
      <div className="modal-content">
        <div className="modal-header">
          <h5 className="modal-title ps-2">Managers</h5>
          <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
        </div>
        <div className="modal-body">
          {this.props.managers && this.props.managers.map(manager => (
            <p key={manager.id}>
              Name: <span className="fw-bold">{manager.name}</span>.
              Email: <span className="fw-bold">{manager.email}</span>
            </p>
          ))}
        </div>
      </div>
    );
  }
}

export default ManagerContactsInnerModal;