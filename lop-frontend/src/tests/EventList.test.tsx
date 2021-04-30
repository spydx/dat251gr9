import { EventList } from "../components/EventList";
import { Event } from "../api/types";
import event1 from "./testData/testEvent1.json";
import event2 from "./testData/testEvent2.json";
import { render } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";

const sampleEvents: Event[] = [event1, event2];

test("A EventList contains EventCard elements", () => {
  const eventlistcomp = (
    <MemoryRouter>
      <EventList events={sampleEvents} />
    </MemoryRouter>
  );

  const { getByText } = render(eventlistcomp);
  const knarvik = getByText("Knarvik Mila");
  const sftr = getByText("Stranda Fjord Trail Race");
  expect(knarvik).toBeInTheDocument();
  expect(sftr).toBeInTheDocument();
});
