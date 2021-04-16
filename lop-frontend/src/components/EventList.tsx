import { CardDeck, Col, Container, Row } from "react-bootstrap";
import { EventCard } from "./EventCard";
import { Event } from "../types";
import React from "react";

type Props = {
  events: Event[];
};

export const EventList: React.FC<Props> = ({ events }) => {
  const mkCol = (event: Event, i: number) => (
    <Col key={i} xs={12} md={6} lg={4} xl={3} className="p-1">
      <EventCard event={event} />
    </Col>
  );

  return (
    <Container>
      <Row className="row-cols-auto">{events.map(mkCol)}</Row>
    </Container>
  );
};
