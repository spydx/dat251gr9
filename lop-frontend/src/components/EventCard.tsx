import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import { Event } from "../api";

type Props = {
  event: Event;
};

export const EventCard: React.FC<Props> = ({ event }) => {
  return (
    <Card>
      <div className="card-img-top" style={{ backgroundColor: "#eee", height: "200px" }}></div>
      <Card.Body>
        <Card.Link as={Link} to={"/event/" + event.id}>
          <Card.Title>{event.name}</Card.Title>
        </Card.Link>
        <Card.Subtitle>{event.location.place}</Card.Subtitle>
        <Card.Text as="div">
          <div>
            Organizer: <span>{event.organizer?.organizerName || "undefined"}</span>
          </div>
          <div>
            Event start: <span>{event.eventStart}</span>
          </div>
          <div>
            Info: <span>{event.generalInfo}</span>
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
