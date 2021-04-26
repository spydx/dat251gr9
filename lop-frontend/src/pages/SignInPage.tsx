import { Alert, Button, Card, Col, Form, Row } from "react-bootstrap";
import MasterPage from "./MasterPage";
import React, { useState } from "react";
import { useHistory, useLocation } from "react-router";
import { useAuth } from "../auth";

export const SignInPage: React.FunctionComponent = () => {
  const [errorMessage, setErrorMessage] = useState("");
  const history = useHistory();
  const location = useLocation<any>();
  const auth = useAuth();

  function redirectToDestination() {
    const { from } = location.state || { from: { pathname: "/" } };
    history.replace(from);
  }

  if (auth.isSignedIn()) {
    redirectToDestination();
  }

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = event.currentTarget;
    const success = await auth.signIn(formData["formEmail"].value, formData["formPassword"].value);
    if (success) {
      redirectToDestination();
    } else {
      setErrorMessage("Invalid username or password");
    }
  };

  const errorBanner = errorMessage ? (
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
