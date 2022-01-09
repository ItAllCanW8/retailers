import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import LogIn from './component/auth/LogIn';
import Home from './component/Home';
import Profile from './component/auth/Profile';
import SystemAdmin from './component/customers/SystemAdmin';
import Navbar from './component/Navbar';
import 'bootstrap/dist/css/bootstrap.min.css';
import Locations from './component/locations/Locations';
import Users from './component/users/Users';
import Items from './component/items/Items';
import Applications from './component/applications/Applications';
import WarehouseItems from './component/warehouse/WarehouseItems';
import Bills from './component/shop/Bills';
import ShopItems from './component/shop/ShopItems';
import RentalTaxes from './component/taxes/RentalTaxes';
import CategoryTaxes from './component/taxes/CategoryTaxes';
import WriteOffActs from './component/shop/WriteOffActs';

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
            <Route exact path="/profile" component={Profile}/>
            <Route exact path="/system-admin" component={SystemAdmin}/>
            <Route exact path="/locations" component={Locations}/>
            <Route exact path="/users" component={Users}/>
            <Route exact path="/items" component={Items}/>
            <Route exact path="/applications" component={Applications}/>
            <Route exact path="/warehouse" component={WarehouseItems}/>
            <Route exact path="/shop" component={ShopItems}/>
            <Route exact path="/bills" component={Bills}/>
            <Route exact path="/taxes/rental" component={RentalTaxes}/>
            <Route exact path="/taxes/category" component={CategoryTaxes}/>
            <Route exact path="/write-off-acts" component={WriteOffActs}/>
          </div>
        </Switch>
      </Router>
    );
  }
}

export default App;
