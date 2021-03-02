import React from 'react';
import { render } from '@testing-library/react';
import App from '../App';
import '@testing-library/jest-dom/extend-expect';

// test('renders learn react link', () => {
//   render(<App />);
//   const linkElement = screen.getByText(/learn react/i);
//   expect(linkElement).not.toBeInTheDocument;
// });

test('Render App page', () => {
   const { getByText } = render(<App />);
   const footer = getByText("DAT251 Gruppe9");
   expect(footer).toBeInTheDocument();

});