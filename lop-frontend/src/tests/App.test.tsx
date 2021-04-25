import { render } from "@testing-library/react";
import App from "../App";
import "@testing-library/jest-dom/extend-expect";

import { MemoryRouter } from "react-router-dom";

test("Render App page", () => {
  const { getByText } = render(
    /* MemoryRouter: see https://reactrouter.com/web/guides/testing */
    <MemoryRouter>
      <App />
    </MemoryRouter>
  );
  const footer = getByText("DAT251 Gruppe 9");
  expect(footer).toBeInTheDocument();
});
