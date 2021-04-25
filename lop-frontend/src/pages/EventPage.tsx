import { useParams } from "react-router-dom";
import { LoadingSpinner } from "../components/Spinner";
import { Container,Jumbotron,Col,Row } from "react-bootstrap";
import MasterPage from "./MasterPage";
import useSWR from "swr";
import {RaceCard} from "../components/RaceCard";
import { doGet, ApiPath } from "../services/api";
import {Race} from "../types";

type EventPageParams = { id: string };


export const EventPage: React.FunctionComponent = () => {
  let { id } = useParams<EventPageParams>();
  const {data,error} = useSWR(ApiPath.Event(id),doGet);
  if (error) {
    return (
      <MasterPage>
        <Container className="text-center">Unexpected error</Container>
      </MasterPage>
    );
  }
  if (!data) {
    return (
      <MasterPage>
        <h2>Events</h2>
        <Container className="text-center">
          <LoadingSpinner />
          <div>Loading events</div>
        </Container>
      </MasterPage>
    );
  }
  return (
    <MasterPage>
      <Jumbotron fluid >
      <h1 className = "text-center">{`${data.name}`}</h1>
      <p className = "text-center">{`${data.generalInfo}`}</p>
      </Jumbotron>
      <Row>
        {data.races.map((races : Race) => (
          <Col key = {races.id}><RaceCard race = {races}/> </Col>
        ))}
      </Row>
    </MasterPage>
  );
};
