import React, {Component} from "react";
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import LogIn from "./component/auth/LogIn";
import Home from "./component/Home";
import Profile from "./component/auth/Profile";
import SystemAdmin from "./component/system-admin/SystemAdmin";
import SignUp from "./component/auth/SignUp";
import Navbar from "./component/Navbar";
import "bootstrap/dist/css/bootstrap.min.css";
import Locations from './component/admin/Locations';
import Users from './component/admin/Users';
import Items from './component/admin/Items';
import Applications from './component/employee/Applications';

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
        <Navbar/>
        <Switch>
          <div className="container">
            <Route exact path='/' component={Home}/>
            <Route exact path="/login" component={LogIn}/>
            <Route exact path="/signup" component={SignUp}/>
            <Route exact path="/profile" component={Profile}/>
            <Route exact path="/system-admin" component={SystemAdmin}/>
            <Route exact path="/locations" component={Locations}/>
            <Route exact path="/users" component={Users}/>
            <Route exact path="/items" component={Items}/>
            <Route exact path="/applications" component={Applications}/>
          </div>
        </Switch>
      </Router>
    );
  }
}

export default App;
