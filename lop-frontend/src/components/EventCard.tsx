import { Button, Card } from "react-bootstrap";
import { Event } from "../types";

type Props = {
  event: Event;
};

export const EventCard: React.FC<Props> = ({ event }) => {
  return (
    <Card>
      <Card.Img
        variant="top"
        src="**missing**"
        style={{ maxHeight: "200px" }}
      />
      <Card.Body>
        <Card.Link href={"/event/" + event.id}>
          <Card.Title>{event.name}</Card.Title>
        </Card.Link>
        <Card.Subtitle>{event.organizer}</Card.Subtitle>
        <Card.Text as="div">
          Event start: <span>{event.eventstart}</span>
          <br />
          Info: <span>{event.generalinfo}</span>
          <br />
          Distances:
          <ul>
            {event.races.map((race, key) => (
              <li key={key}>{`${race.distance} km`}</li>
            ))}
          </ul>
        </Card.Text>
      </Card.Body>
    </Card>
  );
};
