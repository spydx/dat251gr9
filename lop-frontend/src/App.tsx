import { Switch, Route } from "react-router-dom";
import { About } from "./pages/About";
import { EventPage } from "./pages/EventPage";
import { EventListPage } from "./pages/EventListPage";
import { HomePage } from "./pages/HomePage";
import { SignIn } from "./pages/SignIn";
import { SignUp } from "./pages/SignUp";

const App: React.FunctionComponent = () => {
  return (
    <Switch>
      <Route path="/about" component={About} />
      <Route path="/signIn" component={SignIn} />
      <Route path="/signUp" component={SignUp} />
      <Route path="/event/:id" component={EventPage} />
      <Route path="/events/" component={EventListPage} />
      <Route path="/" component={HomePage} />
    </Switch>
  );
};

export default App;