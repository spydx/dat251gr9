import React from "react";
import { Button, Container } from "react-bootstrap";
import { EventList } from "./components/EventList";
import { LoadingSpinner } from "./components/Spinner";
import Header from "./Header";
import { fetchEventList } from "./services/api";
import { Event } from './types'

type PageState = {
  events: Event[] | undefined
};

export class App extends React.Component<{}, PageState> {
  constructor() {
    super({});
    this.state = { events: undefined }
  }

  componentDidMount() {
    this.refreshEventList()
  }
  
  refreshEventList() {
    // clear the previous state, sets a loading indicator
    this.setState({ events: undefined })
    fetchEventList().then(events => {
      if (events !== undefined) {
        this.setState({ events })
      } else {
        console.log("App::update(): events == undefined")
      }
    })
  }

  render() {
    const eventList = this.state.events
      ? <EventList id={1} events={this.state.events} />
      : <LoadingSpinner />;

    return (
      <Container className="text-center">
        <Header />
        <Button onClick={() => this.refreshEventList()}>Update</Button>
        <br/>
        {eventList}
        <div className="text-muted">DAT251 Gruppe9</div>
      </Container>
    );
  }
}

export default App;