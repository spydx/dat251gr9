import React, { useState } from "react";
import { Button, Row } from "react-bootstrap";
import { SignInForm } from "../components/SignInForm";
import MasterPage from "../MasterPage";
import { backendRoot, LOGIN_PATH, poster } from "../services/api";

export function SignIn() {
  const storedJwt = localStorage.getItem("token");
  const [jwt, setJwt] = useState(storedJwt || null);

  const getJwt = async () => {
    const { token } = await poster(LOGIN_PATH, {
      email: "admin@lop.no",
      password: "admin",
    });
    localStorage.setItem("token", token);
    setJwt(token);
  };

  return (
    <MasterPage>
      <h2>Sign In</h2>
      <Row className="justify-content-md-center">
        {`${jwt}\n`}
        <Button onClick={() => getJwt()}>Click Me!</Button>
      </Row>
    </MasterPage>
  );
}
