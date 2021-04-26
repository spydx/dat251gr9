import { Redirect, Route, Switch } from "react-router-dom";
import { EventListPage, EventPage, HomePage, NotFoundPage, ProfilePage, SignInPage, SignUpPage } from "./pages";
import { ProvideAuth, useAuth } from "./auth";

const SignOut: React.FunctionComponent = () => {
  const auth = useAuth();
  auth.signOut();
  return <Redirect to="/" />;
};

function PrivateRoute({ component: Component, ...rest }: any) {
  const auth = useAuth();
  return (
    <Route
      {...rest}
      render={(props) =>
        auth.isSignedIn() ? (
          <Component {...props} />
        ) : (
          <Redirect to={{ pathname: "/signIn", state: { from: props.location } }} />
        )
      }
    />
  );
}

const App: React.FunctionComponent = () => {
  return (
    <ProvideAuth>
      {
        // todo(H-C-H) 404
        // todo(H-C-H) signedOutOnly
        // todo(H-C-H) signedInOnly
      }
      <Switch>
        <Route exact path="/signIn" component={SignInPage} />
        <Route exact path="/signUp" component={SignUpPage} />
        <PrivateRoute exact path="/signOut" component={SignOut} />
        <PrivateRoute exact path="/profile" component={ProfilePage} />
        <Route exact path="/event/:id" component={EventPage} />
        <Route exact path="/events/" component={EventListPage} />
        <Route exact path="/" component={HomePage} />
        <Route path="*" component={NotFoundPage} />
      </Switch>
    </ProvideAuth>
  );
};

export default App;
