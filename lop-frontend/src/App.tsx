import Header from './Header';
import {Container} from 'react-bootstrap';
import { EventList } from './components/EventList';

function App() {
  
  
  return (
    <Container className="text-center">
      <Header/>
        <EventList />
      <div className="text-muted">DAT251 Gruppe9</div>
    </Container>
  );
}

export default App;