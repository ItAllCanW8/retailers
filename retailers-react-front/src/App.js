import React, {useState, useEffect, Component} from "react";
import Counter from "./component/Counter";
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import axios from "axios";
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

// const UserProfiles = () => {
//     const fetchUserProfiles = () => {
//       axios.get("http://localhost:8080/api/v1/items").then(resp => {
//           console.log(resp);
//       });
//     }
//
//     useEffect(() => {
//         fetchUserProfiles();
//     }, []);
//
//     return <h1>axios</h1>;
// }

export default App;
