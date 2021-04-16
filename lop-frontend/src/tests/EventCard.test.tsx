import React from "react";
import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import { EventCard } from "../components/EventCard";
import event from "./testData/testEvent1.json";
import { Event } from "../types";

const eventCard = <EventCard event={event} />;

// test('A Race event More info', () => {
//    const { getByText } = render(eventCard);
//    const moreinfo = getByText("More Info");
//    expect(moreinfo).toBeInTheDocument();
// });

// test('A Participate button', () => {
//    const {getByText} = render(eventCard);
//    const run = getByText("Skal løpe");
//    expect(run).toBeInTheDocument();
// })

// test('A Interested button', () => {
//    const { getByText } = render(eventCard);
//    const interested = getByText("Interessert");
//    expect(interested).toBeInTheDocument();
// })

test("Has a name", () => {
  const { getByText } = render(eventCard);
  const title = getByText("Knarvik Mila");
  expect(title).toBeInTheDocument();
});

test("Has a Description", () => {
  const { getByText } = render(eventCard);
  const text =
    "Klar for ein klassikar! Distansene blir arrangert for 39. gong i 2021 Er du klar for halvmila? Løp for å leve i samarbeid med Kreftforeningen.";
  const desc = getByText(text);
  expect(desc).toBeInTheDocument();
});

test("Has a Distance", () => {
  const { getByText } = render(eventCard);
  const distance = getByText("10 km");
  expect(distance).toBeInTheDocument();
});

test("Has a EventDay", () => {
  const { getByText } = render(eventCard);
  const date = getByText("13. Mai 2021");
  expect(date).toBeInTheDocument();
});
