import React from 'react';
import Header from './Header';
import {Container} from 'react-bootstrap';
import data from './eventdata.json';
import RaceList from './components/RaceList';
import RaceEvent from './RaceEvent';

function App() {
  
  
  return (
    <Container className="text-center">
      <Header/>
      <RaceEvent />
      <div className="text-muted">DAT251 Gruppe9</div>
    </Container>
  );
}

export default App;


{/* <img src={logo} className="App-logo" alt="logo" />
<p>
  Edit <code>src/App.tsx</code> and save to reload.
</p>
<a
  className="App-link"
  href="https://reactjs.org"
  target="_blank"
  rel="noopener noreferrer"
>
  Learn React
</a> */}