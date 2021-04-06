import { render } from "@testing-library/react";
import App from "../App";
import "@testing-library/jest-dom/extend-expect";
import { Provider } from "react-redux";

import { store } from "../state/store";
import { MemoryRouter } from "react-router-dom";

test("Render App page", () => {
  const { getByText } = render(
    <Provider store={store}>
      {/* routing and testing: https://reactrouter.com/web/guides/testing */}
      <MemoryRouter>
        <App />
      </MemoryRouter>
    </Provider>
  );
  const footer = getByText("DAT251 Gruppe9");
  expect(footer).toBeInTheDocument();
});
