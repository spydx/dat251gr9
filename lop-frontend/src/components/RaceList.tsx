import * as React from 'react';
import { RaceListInterface } from '../interfaces';
import {Container} from 'react-bootstrap';
import RaceEvent from './../RaceEvent';
import data from './eventdata.json';


const RaceList = (props: RaceListInterface) => {
   
   return (
      <Container>
         <RaceEvent/>
         <RaceEvent/>
      </Container>
   );
}

export default RaceList;