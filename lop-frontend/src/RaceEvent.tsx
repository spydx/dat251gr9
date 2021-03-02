import React from 'react';
import {Container, Button, Card,  CardDeck} from 'react-bootstrap';
import {Badge, ListGroup, ListGroupItem} from 'react-bootstrap';
import data from './eventdata.json';

function RaceEvent() {
   return (
      <Container>
         <CardDeck style={{display: 'flex', flexDirection: 'row'}}>
            <Card border="warning" style={{justifyContent: 'center'}}>
               <Card.Img variant='top' src='https://static1.squarespace.com/static/5ab37f44b27e395be60ff059/t/5ab3d0736d2a734e690a9a03/1607938028019/?format=1500w'></Card.Img>
               <Card.Title className="text-center">{data.eventday}</Card.Title>
            </Card>
         
            <Card style={{flex: 5}}>
               <Card.Header>
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
               <Card.Footer className="text-right">
                  <Card.Link>More Info</Card.Link>
                  <Button>
                        <Badge variant="light">9000</Badge>
                        Interessert
                     </Button>
                     <Button>
                     <Badge variant="light">100</Badge> 
                        Skal løpe
                  </Button>
               </Card.Footer>
            </Card>
         </CardDeck>
      </Container>
   );
}

export default RaceEvent;