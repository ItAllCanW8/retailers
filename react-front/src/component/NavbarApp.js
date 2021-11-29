import React, {Component} from 'react';
import jwtDecode from "jwt-decode";
import "bootstrap/dist/css/bootstrap.min.css";
import 'jquery/dist/jquery.min.js'
import 'bootstrap/dist/js/bootstrap.min.js'
import {Link} from "react-router-dom";

export default class NavbarApp extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: undefined,
      showModeratorBoard: false,
      showAdminBoard: false,
      collapse: false
    };
    this.toggle = this.toggle.bind(this);
    this.logOut = this.logOut.bind(this);
  }

  componentDidMount() {
    let user = localStorage.getItem("user");
    if (user) {
      user = jwtDecode(user);
      this.setState({
        currentUser: user,
        showModeratorBoard: user.role.includes("RETAIL_ADMIN"),
        showAdminBoard: user.role.includes("SYSTEM_ADMIN"),
      });
    }
  }

  logOut() {
    localStorage.removeItem("user");
    this.setState({
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  toggle() {
    this.setState({collapse: !this.state.collapse});
  }

  render() {
    const {currentUser, showModeratorBoard, showAdminBoard, collapse} = this.state;

    return <nav className="navbar navbar-expand-sm navbar-light bg-light mb-2">
      <div className="container">
        <button className="navbar-toggler" type="button" data-bs-toggle="collapse" onClick={this.toggle}
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
          <span className="navbar-toggler-icon"/>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <Link className="nav-link" to={"/"}>Home</Link>
            </li>
            {showAdminBoard && (
              <li className="nav-item">
                <Link to={"/admin"} className="nav-link" tabIndex="-1">Admin</Link>
              </li>
            )}
          </ul>
          <div className="d-flex">
            {currentUser ? (
              <div className="navbar-nav ml-auto align-items-center">
                <li className="nav-item">
                  <a href="" className="nav-link" onClick={this.logOut}>
                    Log Out
                  </a>
                </li>

                <li className="nav-item">
                  <Link to={"/profile"} className="nav-link">
                    {currentUser.name}
                    <img src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" alt="profile-img"
                         className="rounded-circle pl-1" width="30"/>
                  </Link>
                </li>
              </div>
            ) : (
              <div className="navbar-nav ml-auto align-items-center">
                <li className="nav-item">
                  <Link to={"/login"} className="nav-link">
                    Login
                  </Link>
                </li>

                <li className="nav-item">
                  <Link to={"/signup"} className="nav-link">
                    Sign Up
                  </Link>
                </li>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  }
}