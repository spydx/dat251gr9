import { EventList } from "../components/EventList";
import { RootState, Event } from "../types";
import event1 from "../eventdata.json";
import event2 from "../eventdata2.json";
import { eventListSelector } from "../modules/eventlist/selectors";
import { useSelector } from "react-redux";
import { render } from "@testing-library/react";
import { store } from "../state/store";

const sampleEvents: Event[] = [event1, event2];

test("A EventList contains EventCard elements", () => {
  const eventlistcomp = <EventList id={1} events={sampleEvents} />;

  const { getByText } = render(eventlistcomp);
  const knarvik = getByText("Knarvik Mila");
  const sftr = getByText("Stranda Fjord Trail Race");
  expect(knarvik).toBeInTheDocument();
  expect(sftr).toBeInTheDocument();
});
