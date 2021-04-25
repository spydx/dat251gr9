import { Alert, Button, Card, Col, Form, Row } from "react-bootstrap";
import MasterPage from "./MasterPage";
import React, { useState } from "react";
import { useHistory } from "react-router";
import { authenticate } from "../api/methods";

/** sends the login request and saves the token */
async function performLogin(email: string, password: string) {
  const result = await authenticate({
    email: email,
    password: password,
  });
  if (!result) {
    return false;
  }
  localStorage.setItem("token", result.token);
  localStorage.setItem("userId", result.userId);
  return true;
}

export const SignInPage: React.FunctionComponent = () => {
  const [errorMessage, setErrorMessage] = useState("");
  const history = useHistory();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = event.currentTarget;
    if (await performLogin(formData["formEmail"].value, formData["formPassword"].value)) {
      history.push("/"); // TODO: for now, we just always redirect to home
    } else {
      setErrorMessage("Invalid username or password");
    }
  };

  let errorBanner = errorMessage ? (
    <Alert variant="danger" dismissible onClose={() => setErrorMessage("")}>
      {errorMessage}
    </Alert>
  ) : null;

  return (
    <MasterPage>
      <h2>Sign In</h2>
      <Row className="justify-content-center">
        <Col xs="auto">
          {errorBanner}
          <Card>
            <Card.Body>
              <Form noValidate className="signInForm" onSubmit={handleSubmit}>
                <Form.Group controlId="formEmail">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control name="email" required type="email" placeholder="Enter email" />
                </Form.Group>

                <Form.Group controlId="formPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control name="password" required type="password" placeholder="Password" />
                </Form.Group>

                {/* Enhancement: add a "remember me" checkbox
              <Form.Group controlId="formCheckbox">
                <Form.Check type="checkbox" label="Remember me" />
              </Form.Group> */}
                <div className="text-right">
                  <Button variant="primary" type="submit">
                    Sign In
                  </Button>
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </MasterPage>
  );
};
