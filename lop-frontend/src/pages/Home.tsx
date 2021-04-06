import { Container } from "react-bootstrap";
import { EventList } from "../components/EventList";
import { LoadingSpinner } from "../components/Spinner";
import MasterPage from "../MasterPage";
import { doGet, ApiPath } from "../services/api";
import useSWR from 'swr';


export const Home = () => {

  const { data, error } = useSWR(ApiPath.Events, doGet);

  
  if (error) return (
    <MasterPage>
      <Container className="text-center">
          <Container>
            Unexpected error
          </Container>
      </Container>
    </MasterPage>
  )

  if (!data) return (
    <MasterPage>
      <Container className="text-center">
        <Container> 
          <LoadingSpinner /> 
          Loading 
        </Container>
      </Container>
    </MasterPage>
  )


  return (
    <MasterPage>
      <Container className="text-center">
        <h2>Home</h2>
        <EventList id={1} events={data} />
      </Container>
    </MasterPage>
  );
  
}


