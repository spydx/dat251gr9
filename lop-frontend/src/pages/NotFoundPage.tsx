import React from "react";
import { Link } from "react-router-dom";
import MasterPage from "./MasterPage";

export const NotFoundPage: React.FunctionComponent = () => {
  return (
    <MasterPage>
      <h2>Not Found</h2>
      <p>The page you are looking for does not exist</p>
      <Link to="/">Go Home</Link>
    </MasterPage>
  );
};
