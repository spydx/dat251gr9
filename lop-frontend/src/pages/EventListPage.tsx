import { Container } from "react-bootstrap";
import { EventList } from "../components/EventList";
import { LoadingSpinner } from "../components/Spinner";
import MasterPage from "./MasterPage";
import { doGet, ApiPath } from "../services/api";
import useSWR from "swr";

export const EventListPage: React.FunctionComponent = () => {
  const { data, error } = useSWR(ApiPath.Events, doGet);
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
      <h2>Events</h2>
      <EventList events={data} />
    </MasterPage>
  );
};
