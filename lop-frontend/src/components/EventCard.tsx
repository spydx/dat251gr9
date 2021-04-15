import { Container, Button, Card } from "react-bootstrap";
import { Badge, ListGroup, ListGroupItem } from "react-bootstrap";
import { Event } from "../types";

type EventCardProps = {
  id: number;
  event: Event;
};

export const EventCard = ({ event }: EventCardProps) => {
  return (
    <Container>
      <Card style={{ display: "flex" }}>
        <Card.Header>
          <Card>
            IMAGE
            <Card.Title className="text-center">{event.eventstart}</Card.Title>
          </Card>
          <Card.Title>{event.name}</Card.Title>
        </Card.Header>
        <Card.Body>{event.generalinfo}</Card.Body>
        <ListGroup className="text-right">
          {event.races.map((r, i) => (
            <ListGroupItem key={i}>Distanse {r.distance} KM</ListGroupItem>
          ))}
        </ListGroup>
        <Card.Footer
          style={{
            alignContent: "space-between",
            justifyContent: "space-between",
          }}
        >
          <Card.Link>More Info</Card.Link>
          <Button>
            Interessert
            <Badge variant="light">9000</Badge>
          </Button>
          <Button style={{ justifyContent: "space-between" }}>
            Skal løpe
            <Badge variant="light">100</Badge>
          </Button>
        </Card.Footer>
      </Card>
    </Container>
  );
};
