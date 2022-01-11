import React, { Component } from 'react';

class StateCodes extends Component {
  render() {
    return (
      <div>
      <label
        htmlFor='stateCode'
        className='form-label'
      >
        State code
      </label>
      <select
        className='form-select'
        aria-label='Default select example'
        name='stateCode'
        id='stateCode'
        aria-describedby='inputGroupPrepend'
        required
        autoComplete='off'
        value={this.props.stateCode}
        onChange={this.props.onAddressChange}
      >
        <option value="AK">AK</option>
        <option value="AZ">AZ</option>
        <option value="AR">AR</option>
        <option value="CA">CA</option>
        <option value="CO">CO</option>
        <option value="CT">CT</option>
        <option value="DE">DE</option>
        <option value="FL">FL</option>
        <option value="GA">GA</option>
        <option value="HI">HI</option>
        <option value="ID">ID</option>
        <option value="IL">IL</option>
        <option value="IN">IN</option>
        <option value="IA">IA</option>
        <option value="KS">KS</option>
        <option value="KY">KY</option>
        <option value="LA">LA</option>
        <option value="ME">ME</option>
        <option value="MD">MD</option>
        <option value="MA">MA</option>
        <option value="MI">MI</option>
        <option value="MN">MN</option>
        <option value="MS">MS</option>
        <option value="MO">MO</option>
        <option value="MT">MT</option>
        <option value="NE">NE</option>
        <option value="NV">NV</option>
        <option value="NH">NH</option>
        <option value="NJ">NJ</option>
        <option value="NM">NM</option>
        <option value="NY">NY</option>
      </select>
      </div>
    );
  }
}

export default StateCodes;