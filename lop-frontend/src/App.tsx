import Header from './Header';
import {Container} from 'react-bootstrap';
import RaceEvent from './RaceEvent';
import Counter from './feature/Counter';

function App() {
  
  
  return (
    <Container className="text-center">
      <Header/>
      <RaceEvent />
      <div className="text-muted">DAT251 Gruppe9</div>
      <Counter />
    </Container>
  );
}

export default App;