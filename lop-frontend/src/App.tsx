import Header from './Header';
import { RootState} from "./types";
import {Container, Button} from 'react-bootstrap';
import { EventList } from './components/EventList';
import { useDispatch, useSelector } from 'react-redux';
import { eventListSelector } from './modules/eventlist/selectors';
import { store } from './store';
import { fetchEventListFromApi } from './modules/eventlist/actions';

export const App: React.FC = () =>  {
  const eventlist = useSelector<RootState, RootState["eventList"]>(
    eventListSelector
  )
  const dispatch = useDispatch()
  
  const update = () => {
    dispatch(fetchEventListFromApi())
  }
  
  const renderEventList = () => {
    <EventList id={1} eventlist={eventlist} />
  }
  store.subscribe(renderEventList)

  return (
    <Container className="text-center">
      <Header/>
        {renderEventList}
        <Button onClick={update}>Update</Button>
      <div className="text-muted">DAT251 Gruppe9</div>
    </Container>
  );
}

export default App;