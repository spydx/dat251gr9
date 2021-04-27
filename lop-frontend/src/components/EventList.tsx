
import { Container } from 'react-bootstrap';
import { EventCard }  from './EventCard';
import { Event } from "../types";

type EventListProps = {
   id: number,
   events: Event[]
}

export const EventList = ({events}: EventListProps) => {
      return (
        <Container>
          {events.map((event, i) => (
            <EventCard key={i} id={i} event={event} />
          ))}
        </Container>
      );    
}