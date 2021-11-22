import React, {useState, useEffect, Component} from "react";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Items from "./component/Items";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/items' exact={true} component={Items}/>
                </Switch>
            </Router>
        );
    }
}

export default App;
