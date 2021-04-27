import { Card, Button,ListGroup } from "react-bootstrap";
import {Race} from "../api/types";
import * as React from "react";


function BxXIcon(props: React.SVGProps<SVGSVGElement>) {
  return (
    <svg
      viewBox="0 0 24 24"
      fill="Red"
      height="2em"
      width="2em"
      {...props}
    >
      <path d="M16.192 6.344l-4.243 4.242-4.242-4.242-1.414 1.414L10.535 12l-4.242 4.242 1.414 1.414 4.242-4.242 4.243 4.242 1.414-1.414L13.364 12l4.242-4.242z" />
    </svg>
  );
}

function BxCheckIcon(props: React.SVGProps<SVGSVGElement>) {
  return (
    <svg
      viewBox="0 0 24 24"
      fill="Green"
      height="2em"
      width="2em"
      {...props}
    >
      <path d="M10 15.586l-3.293-3.293-1.414 1.414L10 18.414l9.707-9.707-1.414-1.414z" />
    </svg>
  );
}


type Props = {
    race: Race;
  };
  

export const RaceCard: React.FC<Props> = ({ race }) => {
  return (
    <Card>
      <div className="card-img-top" style={{ backgroundColor: "#eee", height: "200px" }}></div>
      <Card.Body>
          <Card.Title className = "text-center">{race.info}</Card.Title>
        <Card.Text as="div">
        <ListGroup variant = "flush" as="ul">
            <ListGroup.Item className = "text-center" as="li" active>
                Informasjon
            </ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li">Bakkeløp: {race.hillRun ? (<BxCheckIcon/>) : (<BxXIcon/>)}</ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li">Stigning: <span>{race.elevation} meter</span></ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li">Distanse: <span>{race.distance} km</span></ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li"> Barn: {race.children ? (<BxCheckIcon/>) : (<BxXIcon/>)}</ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li">Stafett: {race.relay ? (<BxCheckIcon />) : (<BxXIcon/>)}</ListGroup.Item>
            <ListGroup.Item className = "text-center" as="li">Hinder: {race.obstacleRun ? (<BxCheckIcon />) : (<BxXIcon/>)}</ListGroup.Item>
        </ListGroup> 
        </Card.Text>
        <Button variant="primary" block>Meld deg på</Button>{' '}
      </Card.Body>
    </Card>
  );
};