import React from 'react';
import { getByTestId, render, screen } from '@testing-library/react';
import App from './App';
import '@testing-library/jest-dom/extend-expect';

// test('renders learn react link', () => {
//   render(<App />);
//   const linkElement = screen.getByText(/learn react/i);
//   expect(linkElement).not.toBeInTheDocument;
// });

test('Test', () => {
  render(<App />)
  const element = screen.getByTestId("text-content");
  expect(element).toHaveTextContent("Dette er logo.png")
})
