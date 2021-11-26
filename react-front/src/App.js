import React, {Component} from "react";
import {BrowserRouter as Router, Link, Route, Switch} from 'react-router-dom';
import Items from "./component/Items";
import Login from "./component/Login";
import SingleItem from "./component/SingleItem";
import Home from "./component/Home";
import Profile from "./component/Profile";
import BoardAdmin from "./component/BoardAdmin";
import Register from "./component/Register";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    };
  }

  logOut() {
    localStorage.removeItem("user");
    this.setState({
      showModeratorBoard: false,
      showAdminBoard: false,
      currentUser: undefined,
    });
  }

  render() {
    return (
      <Router>
        <div className="navbar-nav ml-auto">
          <li className="nav-item">
            <Link to={"/login"} className="nav-link">
              Login
            </Link>
          </li>

          <li className="nav-item">
            <Link to={"/register"} className="nav-link">
              Sign Up
            </Link>
          </li>

          <li className="nav-item">
            <a href="/logout" className="nav-link" onClick={this.logOut}>
              LogOut
            </a>
          </li>
        </div>
        <Switch>
          <Route path='/home' exact={true} component={Home}/>
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/profile" component={Profile} />
          <Route path='/items' exact={true} component={Items}/>
          <Route path='/items/:id' component={SingleItem}/>
          <Route path="/admin" component={BoardAdmin} />
        </Switch>
      </Router>
    );
  }
}

export default App;
