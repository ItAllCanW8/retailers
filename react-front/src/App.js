import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route, Switch} from 'react-router-dom';
import Items from "./component/Items";
import LogIn from "./component/LogIn";
import Home from "./component/Home";
import Profile from "./component/Profile";
import BoardAdmin from "./component/BoardAdmin";
import SignUp from "./component/SignUp";
import NavbarApp from "./component/NavbarApp";
import {Container} from "reactstrap";
import "bootstrap/dist/css/bootstrap.min.css";

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
        <NavbarApp/>
          <Switch>

            <Route path='/home' exact={true} component={Home}/>
            <Route exact path="/login" component={LogIn}/>
            <Route exact path="/register" component={SignUp}/>
            <Route exact path="/profile" component={Profile}/>
            <Route path='/items' exact={true} component={Items}/>
            <Route path="/admin" component={BoardAdmin}/>
          </Switch>
      </Router>
    );
  }
}

export default App;
