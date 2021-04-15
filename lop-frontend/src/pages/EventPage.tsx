import { useParams } from "react-router-dom";
import MasterPage from "../MasterPage";

type EventPageParams = { id: string };

export const EventPage: React.FunctionComponent = () => {
  let { id } = useParams<EventPageParams>();
  return (
    <MasterPage>
      <h2>{`Event with id ${id}`}</h2>
    </MasterPage>
  );
};
