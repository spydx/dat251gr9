import React from "react";
import { Button, Container } from "react-bootstrap";
import { EventList } from "../components/EventList";
import { LoadingSpinner } from "../components/Spinner";
import Header from "../Header";
import MasterPage from "../MasterPage";
import { fetchEventList } from "../services/api";
import { Event } from "../types";

type PageState = {
  events: Event[] | undefined;
};

// we currently use the event list as the home page
export class Home extends React.Component<{}, PageState> {
  constructor(props: {}) {
    super(props);
    this.state = { events: undefined };
  }

  componentDidMount() {
    this.refreshEventList();
  }

  refreshEventList() {
    // clear the previous state, sets a loading indicator
    this.setState({ events: undefined });
    fetchEventList().then((events) => {
      if (events !== undefined) {
        this.setState({ events });
      } else {
        console.log("Home::update(): events == undefined");
      }
    });
  }

  render() {
    const eventList = this.state.events ? <EventList id={1} events={this.state.events} /> : <LoadingSpinner />;

    return (
      <MasterPage>
        <Container className="text-center">
          <h2>Home</h2>
          <Button onClick={() => this.refreshEventList()}>Update</Button>
          {eventList}
        </Container>
      </MasterPage>
    );
  }
}

export default Home;
