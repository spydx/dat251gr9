import React from "react";
import { Button, Container } from "react-bootstrap";
import { EventList } from "./components/EventList";
import Header from "./Header";
import { fetchEventList } from "./services/api";
import { Event } from './types'

type MyState = {
  events: Event[]
};

export class App extends React.Component<{}, MyState> {
  constructor() {
    super({});
    this.state = { events: [] }
  }

  componentWillMount() {
    const the_promise = fetchEventList();
    the_promise.then(events => {
      if (events !== undefined) {
        this.setState({ events })
      } else {
        console.log("had an error fetching state")
      }
    })
  }

  render() {
    function update() {
      console.log("update called, but not implemented");
    }

    const eventList = this.state.events;

    return (
      <Container className="text-center">
        <Header />
        <EventList id={1} events={eventList} />
        <Button onClick={update}>Update</Button>
        <div className="text-muted">DAT251 Gruppe9</div>
      </Container>
    );
  }
}

export default App;