import React from 'react';
import { render } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import Header from '../Header';

test('Navbar Login', () => {
   const { getByText  } = render(<Header />);
   const navbarlogin = getByText("Login");
   expect(navbarlogin).toBeInTheDocument(); 
})

test('Navbar Register', () => {
   const {getByText } = render(<Header />);
   const navbarreg = getByText("Register");
   expect(navbarreg).toBeInTheDocument();
})