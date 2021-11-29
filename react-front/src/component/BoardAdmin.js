import React, {Component, createRef} from 'react';
import UserService from "../service/UserService";
import EventBus from "../common/EventBus";
import axios from "axios";

const API_URL = "http://localhost:8080/api/";

class BoardAdmin extends Component {
  constructor(props) {
    super(props);
    this.formRef = createRef();
    this.state = {
      name: "",
      email: ""
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    const target = event.target;
    this.setState({
      [target.name]: target.value
    });
  }

  handleSubmit(event) {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.checkValidity()) {
      this.state.isFormValidated = true;
      axios.post(API_URL + "admin", this.state);
    } else {
      this.setState({isFormValidated: false});
    }
  }

  componentDidMount() {
    UserService.getAdminBoard().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
      }
    );
  }

  render() {
    let isFormValidated = this.state.isFormValidated;
    return (
      <div>
        <button type="button" className="btn btn-primary" data-bs-toggle="modal" data-bs-target="#customerModal">
          Add customer
        </button>
        <form className={isFormValidated || isFormValidated === undefined ? "row g-3 needs-validation" : "row g-3 needs-validation was-validated"}
              noValidate onSubmit={this.handleSubmit}>

          <div className="modal fade" id="customerModal" tabIndex="-1" aria-labelledby="exampleModalLabel"
               aria-hidden="true">
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title" id="exampleModalLabel">Add customer</h5>
                  <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"/>
                </div>
                <div className="modal-body">

                  <div className="col mb-2">
                    <label htmlFor="validationCustomUsername" className="form-label">Username</label>
                    <div className="input-group has-validation">
                      <input type="text" className="form-control" name="name" id="name"
                             aria-describedby="inputGroupPrepend" required autoComplete="off"
                             value={this.state.name || ''} onChange={this.handleChange}/>
                      <div className="invalid-feedback">
                        Please choose a username.
                      </div>
                    </div>
                  </div>
                  <div className="col">
                    <label htmlFor="validationCustom03" className="form-label">Email</label>
                    <input type="email" className="form-control" name="email" id="email" required autoComplete="off"
                           value={this.state.email || ''} onChange={this.handleChange}/>
                    <div className="invalid-feedback">
                      Please enter a valid email
                    </div>
                  </div>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                  <button type="submit" className="btn btn-primary">Add</button>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
    );
  }
}

export default BoardAdmin;