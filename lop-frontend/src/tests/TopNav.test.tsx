import React from "react";
import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import TopNav from "../TopNav";
import { MemoryRouter } from "react-router-dom";

test("Navbar Sign In", () => {
  const { getByText } = render(
    <MemoryRouter>
      <TopNav />
    </MemoryRouter>
  );
  const navbarlogin = getByText("Sign In");
  expect(navbarlogin).toBeInTheDocument();
});

test("Navbar Sign Up", () => {
  const { getByText } = render(
    <MemoryRouter>
      <TopNav />
    </MemoryRouter>
  );
  const navbarreg = getByText("Sign Up");
  expect(navbarreg).toBeInTheDocument();
});
