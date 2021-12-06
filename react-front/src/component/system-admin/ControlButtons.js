import React, { Component } from 'react';

export class ControlButtons extends Component {
  render() {
    return (
      <div className="row align-items-center mb-3">
        <div className="col align-items-center">
          <button
            type="button"
            className="btn btn-primary me-3"
            onClick={this.props.onClick}
          >
            Add customer
          </button>
          <div className="form-check form-check-inline">
            <input
              className="form-check-input"
              type="radio"
              name="radioOption"
              id="inlineRadio1"
              value="All"
              checked={this.props.radioOption === 'All'}
              onChange={this.props.onChange}
            />
            <label className="form-check-label" htmlFor="inlineRadio1">
              All
            </label>
          </div>
          <div className="form-check form-check-inline">
            <input
              className="form-check-input"
              type="radio"
              name="radioOption"
              id="inlineRadio2"
              value="Only active"
              checked={this.props.radioOption === 'Only active'}
              onChange={this.props.onChange}
            />
            <label className="form-check-label" htmlFor="inlineRadio2">
              Only active
            </label>
          </div>
          <div className="form-check form-check-inline">
            <input
              className="form-check-input"
              type="radio"
              name="radioOption"
              id="inlineRadio3"
              value="Only inactive"
              checked={this.props.radioOption === 'Only inactive'}
              onChange={this.props.onChange}
            />
            <label className="form-check-label" htmlFor="inlineRadio3">
              Only inactive
            </label>
          </div>
        </div>
      </div>
    );
  }
}
