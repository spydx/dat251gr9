import React from "react";
import { Container } from "react-bootstrap";
import {
  Switch,
  Route,
  Link
} from "react-router-dom";
import Header from "./Header";
import MasterPage from "./MasterPage";
import { About } from "./pages/About";
import { Home } from "./pages/Home";
import { SignIn } from "./pages/SignIn";
import { SignUp } from "./pages/SignUp";

export default function App() {
  return (
    <Switch>
      <Route path="/about" component={About} />
      <Route path="/signIn" component={SignIn} />
      <Route path="/signUp" component={SignUp} />
      <Route path="/" component={Home} />
    </Switch>
  );
}