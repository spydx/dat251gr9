
import { Container } from 'react-bootstrap';
import { EventCard }  from './EventCard';
import { EventListProps } from "../types";



export const EventList = ({eventlist}: EventListProps) => {

      return (
         <Container>
            { eventlist.events.map((event, i) => (
               <EventCard key={i} id={i} event={event} />
            ))}
         </Container>
      );
   
   
}