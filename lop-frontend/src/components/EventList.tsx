
import { Container } from 'react-bootstrap';
import { EventCard }  from './EventCard';
import { EventListProps } from "../types";



export const EventList = ({eventlist}: EventListProps) => {
   if(eventlist.events === undefined) {
      return <Container>Unable to fecth data from database</Container>
   } else {
      return (
         <Container>
            { eventlist.events.map((event, i) => (
               <EventCard key={i} id={i} event={event} />
            ))}
         </Container>
      );
   
   }
}