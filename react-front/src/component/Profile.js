import {Component} from "react";
import AuthService from "../service/AuthService";
import {Redirect} from "react-router-dom";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      redirect: null,
      userReady: false,
      currentUser: {username: ""}
    };
  }

  componentDidMount() {
    const currentUser = AuthService.getCurrentUser();

    if (!currentUser) this.setState({redirect: "/home"});
    this.setState({currentUser: currentUser, userReady: true})
  }

  render() {
    if (this.state.redirect) {
      return <Redirect to={this.state.redirect}/>
    }

    const {currentUser} = this.state;

    return (
      <div className="container">
        {(this.state.userReady) ?
          <div>
            <header className="jumbotron">
              <h3>
                <strong>{currentUser.username}</strong> Profile
              </h3>
            </header>
            <p>
              <strong>Id:</strong>{" "}
              {currentUser.id}
            </p>
            <p>
              <strong>Email:</strong>{" "}
              {currentUser.email}
            </p>
            <strong>Authorities:</strong>
            <ul>
              {currentUser.role &&
              currentUser.role.map((role, index) => <li key={index}>{role}</li>)}
            </ul>
          </div> : null}
      </div>
    );
  }
}