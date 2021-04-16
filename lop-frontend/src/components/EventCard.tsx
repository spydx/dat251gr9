import { Card } from "react-bootstrap";
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
        <Card.Subtitle>{event.location.place}</Card.Subtitle>
        <Card.Text as="div">
          <div>
            Organizer: <span>{event.organizer?.organizerName || "undefined"}</span>
          </div>
          <div>
            Event start: <span>{event.eventstart}</span>
          </div>
          <div>
            Info: <span>{event.generalinfo}</span>
          </div>
          <div>
            Distances:
            <ul>
              {event.races.map((race, key) => (
                <li key={key}>{`${Math.round(race.distance)} km`}</li>
              ))}
            </ul>
          </div>
        </Card.Text>
      </Card.Body>
    </Card>
  );
};
