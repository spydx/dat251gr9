import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import RaceEvent from '../RaceEvent';


test('A Race event More info', () => {
   const { getByText } = render(<RaceEvent />);
   const moreinfo = getByText("More Info");
   expect(moreinfo).toBeInTheDocument();
});

test('A Participate button', () => {
   const {getByText} = render(<RaceEvent />);
   const run = getByText("Skal løpe");
   expect(run).toBeInTheDocument();
})

test('A Interested button', () => {
   const { getByText } = render(<RaceEvent />);
   const interested = getByText("Interessert");
   expect(interested).toBeInTheDocument();
})

test('Has a name', () => {
   const { getByText } = render(<RaceEvent/>);
   const title = getByText("Knarvik Mila");
   expect(title).toBeInTheDocument();
})
test('Has a Description', () => {
   const {getByText} = render(<RaceEvent />);
   const text = "Klar for ein klassikar! Distansene blir arrangert for 39. gong i 2021 Er du klar for halvmila? Løp for å leve i samarbeid med Kreftforeningen.";
   const desc = getByText(text);
   expect(desc).toBeInTheDocument();
})

test('Has a Distance', () => {
   const { getByText} = render(<RaceEvent />);
   const distance = getByText("Distanse 10 KM" );
   expect(distance).toBeInTheDocument();
})

test('Has a EventDay', () => {
   const { getByText } = render(<RaceEvent />);
   const date = getByText("13. Mai 2021");
   expect(date).toBeInTheDocument();
})