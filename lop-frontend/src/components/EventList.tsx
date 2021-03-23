
import { Container } from 'react-bootstrap';
import { EventCard }  from './EventCard';
import { rootReducer } from '../rootReducer';



export const EventList = () => {
   const { eventList } = rootReducer()

   return (
      <Container>
         { eventList.events.map((event, i) => (
            <EventCard id={i} event={event} />
         ))}
      </Container>
   );
}