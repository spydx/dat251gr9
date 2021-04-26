import React, { useEffect, useState } from "react";
import MasterPage from "./MasterPage";
import { useAuth } from "../auth";
import { useHistory } from "react-router";
import { Alert, Button, Card, Col, Form, Row } from "react-bootstrap";
import * as api from "../api";
import { Link } from "react-router-dom";

// TODO(H-C-H): It seems to work, but we don't do ANY validation. Also, it's extremely verbose
export const SignUpPage: React.FunctionComponent = () => {
  const auth = useAuth();
  const history = useHistory();

  useEffect(() => {
    if (auth.isSignedIn()) {
      history.replace("/");
    }
  });

  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = event.currentTarget;
    const fields = {
      address: formData["formAddress"].value,
      birthdate: formData["formBirthdate"].value,
      city: formData["formCity"].value,
      email: formData["formEmail"].value,
      firstname: formData["formFirstname"].value,
      lastname: formData["formLastname"].value,
      password: formData["formPassword"].value,
    };

    const signUpSuccess = await api.createUser(fields);
    if (signUpSuccess) {
      // The API doesn't return a token on success, so we must log in with a separate request.
      const signInSuccess = await auth.signIn(fields.email, fields.password);
      if (!signInSuccess) {
        // We assume this never happens
      }
      history.replace("/");
    } else {
      setErrorMessage("Unable to create account");
    }
  };

  const errorBanner = errorMessage ? (
    <Alert variant="danger" dismissible onClose={() => setErrorMessage("")}>
      {errorMessage}
    </Alert>
  ) : null;

  return (
    <MasterPage>
      <h2>Sign Up</h2>
      <Row className="justify-content-center">
        <Col xs="auto">
          {errorBanner}
          <Card>
            <Card.Body>
              <Form noValidate onSubmit={handleSubmit}>
                <Form.Group controlId="formFirstname">
                  <Form.Label>First name</Form.Label>
                  <Form.Control
                    required
                    name="First name"
                    type="text"
                    placeholder="Enter First name"
                  />
                </Form.Group>
                <Form.Group controlId="formLastname">
                  <Form.Label>Last name</Form.Label>
                  <Form.Control
                    required
                    name="Last name"
                    type="text"
                    placeholder="Enter Last name"
                  />
                </Form.Group>

                <Form.Group controlId="formBirthdate">
                  <Form.Label>Birthdate</Form.Label>
                  <Form.Control required name="Birth date" type="date" />
                </Form.Group>

                <Form.Group controlId="formAddress">
                  <Form.Label>Address</Form.Label>
                  <Form.Control required name="Address" type="text" placeholder="Enter Address" />
                </Form.Group>
                <Form.Group controlId="formCity">
                  <Form.Label>City</Form.Label>
                  <Form.Control required name="City" type="text" placeholder="Enter City" />
                </Form.Group>

                <Form.Group controlId="formEmail">
                  <Form.Label>Email</Form.Label>
                  <Form.Control required name="Email" type="email" placeholder="Enter Email" />
                </Form.Group>
                <Form.Group controlId="formPassword">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    required
                    name="Password"
                    type="password"
                    placeholder="Enter Password"
                  />
                </Form.Group>

                <div className="text-right">
                  <Button variant="primary" type="submit">
                    Sign Up
                  </Button>
                </div>

                <div className="pt-3">
                  Already registered? <Link to="/signIn">Sign in</Link>.
                </div>
              </Form>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </MasterPage>
  );
};
