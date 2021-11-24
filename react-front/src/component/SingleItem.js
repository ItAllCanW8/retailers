import React, {Component} from 'react';
import axios from "axios";

class SingleItem extends Component {
    constructor(props) {
        super(props);
        this.state = {items: []};
    }

    componentDidMount() {
        axios.get(`http://localhost:8080/items/${this.props.match.params.id}`)
            .then(data => this.setState({items: data}));
    }

    render() {
        return (
            <div>
                <h1>{this.state.items}</h1>
            </div>
        );
    }
}

export default SingleItem;