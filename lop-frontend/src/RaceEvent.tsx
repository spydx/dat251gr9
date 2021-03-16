import React from 'react';
import {Container, Button, Card,  CardDeck} from 'react-bootstrap';
import {Badge, ListGroup, ListGroupItem} from 'react-bootstrap';
import data from './eventdata.json';

function RaceEvent() {
   return (
      <Container>
            <Card style={{display: 'flex'}} >
               <Card.Header>
                  <Card border="warning" style={{justifyContent: 'center'}}>
                     <Card.Img variant='top' src='https://static1.squarespace.com/static/5ab37f44b27e395be60ff059/t/5ab3d0736d2a734e690a9a03/1607938028019/?format=1500w'></Card.Img>
                     <Card.Title className="text-center">{data.eventday}</Card.Title>
                  </Card>
                  <Card.Title>{data.name}</Card.Title>
               </Card.Header>
               <Card.Body>
                  {data.generalinfo}
               </Card.Body>
               <ListGroup className="text-right">
                  <ListGroupItem>
                     Distanse {data.races[0].distance} KM
                  </ListGroupItem>
                  <ListGroupItem>Distanse {data.races[1].distance} KM
                  </ListGroupItem>
               </ListGroup>
               <Card.Footer style={{ alignContent: 'space-between', justifyContent: 'space-between'}}>
                  <Card.Link>More Info</Card.Link>
                  <Button>
                     Interessert
                        <Badge variant="light">9000</Badge>
                        
                  </Button>
                  <Button style={{justifyContent: 'space-between'}}>
                     Skal løpe
                     <Badge variant="light">100</Badge>   
                  </Button>
               </Card.Footer>
            </Card>
      </Container>
   );
}

export default RaceEvent;