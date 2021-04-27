import React from "react";
import MasterPage from "./MasterPage";
import { useAuth } from "../auth";

export const ProfilePage: React.FunctionComponent = () => {
  const auth = useAuth();

  return (
    <MasterPage>
      <h2>User Profile</h2>
      <p>User ID is {auth.userId}</p>
    </MasterPage>
  );
};
