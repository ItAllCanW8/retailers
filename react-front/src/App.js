import React, {Component} from "react";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Items from "./component/Items";
import Login from "./component/Login";
import SingleItem from "./component/SingleItem";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/items' exact={true} component={Items}/>
                    <Route path='/login' exact={true} component={Login}/>
                    <Route path='/items/:id' component={SingleItem}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
