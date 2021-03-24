import Header from './Header';
import { RootState} from "./types";
import {Container} from 'react-bootstrap';
import { EventList } from './components/EventList';
import { useSelector } from 'react-redux';
import { eventListSelector } from './modules/eventlist/selectors';

export const App: React.FC = () =>  {
  const eventlist = useSelector<RootState, RootState["eventList"]>(
    eventListSelector
  )
  
  return (
    <Container className="text-center">
      <Header/>
        <EventList id={1} eventlist={eventlist} />
      <div className="text-muted">DAT251 Gruppe9</div>
    </Container>
  );
}

export default App;