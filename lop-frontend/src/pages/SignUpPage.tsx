import React from "react";
import MasterPage from "./MasterPage";
import { useAuth } from "../auth";
import { useHistory } from "react-router";

export const SignUpPage: React.FunctionComponent = () => {
  const auth = useAuth();
  const history = useHistory();

  if (auth.isSignedIn()) {
    history.replace("/");
  }

  return (
    <MasterPage>
      <h2>Sign Up</h2>
    </MasterPage>
  );
};
