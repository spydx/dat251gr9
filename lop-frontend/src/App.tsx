import { Switch, Route } from "react-router-dom";
import { SignInPage, SignUpPage, EventPage, EventListPage, HomePage } from "./pages";

const App: React.FunctionComponent = () => {
  return (
    <Switch>
      <Route path="/signIn" component={SignInPage} />
      <Route path="/signUp" component={SignUpPage} />
      <Route path="/event/:id" component={EventPage} />
      <Route path="/events/" component={EventListPage} />
      <Route path="/" component={HomePage} />
    </Switch>
  );
};

export default App;
