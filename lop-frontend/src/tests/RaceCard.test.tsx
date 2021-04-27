import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import { RaceCard } from "../components/RaceCard";
import event from "./testData/testEvent1.json";
import { MemoryRouter } from "react-router-dom";

const raceCard = (
  <MemoryRouter>
    <RaceCard race={event.races[0]} />
  </MemoryRouter>
);

test("Has a condition children", () => {
    const {getByText} = render(raceCard);
    const children = getByText("Barn:");
    expect(children).toBeInTheDocument();
});

test("Has a distance", () => {
    const {getByText} = render(raceCard);
    const distance = getByText("10 km");
    expect(distance).toBeInTheDocument();
    
});