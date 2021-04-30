import { EventList } from "../components/EventList";
import { LoadingSpinner } from "../components/Spinner";
import MasterPage from "./MasterPage";
import useSWR from "swr";
import React from 'react';
import { getAllEvents } from "../api/methods";
import {Container, Navbar, Form, FormControl, Button } from "react-bootstrap";

export const EventListPage: React.FunctionComponent = () => {
  const { data, error } = useSWR('api: getAllEvents', getAllEvents);

  if (error) {
    return (
      <MasterPage>
        <Container className="text-center">Unexpected error</Container>
      </MasterPage>
    );
  }

  if (!data) {
    return (
      <MasterPage>
        <h2>Events</h2>
        <Container className="text-center">
          <LoadingSpinner />
          <div>Loading events</div>
        </Container>
      </MasterPage>
    );
  }

  return (
    <MasterPage>
        <Navbar>
          <Navbar.Brand><h2>Events</h2></Navbar.Brand>
          <Form inline action="search">
            <FormControl name="q" id="query" type="text" placeholder="Location/Name" className="mr-sm-2" />
            <Button variant="outline-success" type="submit" >Search</Button>
          </Form>
        </Navbar>
      <EventList events={data} />
    </MasterPage>
  );
};
