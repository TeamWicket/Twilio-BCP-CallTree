import React, {Component} from 'react';
import HighLevel from './HighLevel'
import NumbersCard from "./NumbersCard";
import EventsCard from "./EventsCard";
import ContactsCard from "./ContactsCard";
import BalanceCard from "./BalanceCard";
import StatusCard from "./StatusCard";


class ServerData extends Component {
  render() {
    return (
      [
        <HighLevel serverStats={this.state.stats}/>,
        <NumbersCard serverStats={this.state.stats}/>,
        <EventsCard serverStats={this.state.stats}/>,
        <ContactsCard serverStats={this.state.stats}/>,
        <BalanceCard serverStats={this.state.stats}/>,
        <StatusCard serverStats={this.state.stats}/>,
      ]
    )
  }

  state = {
    stats: []
  };

  componentDidMount() {
    fetch('http://localhost:8080/api/v1/stats/dash')
      .then(res => res.json())
      .then((data) => {
        this.setState({stats: data})
      })
      .catch(console.log)
  }
}

export default ServerData;
