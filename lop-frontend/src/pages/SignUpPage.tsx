import React, { useEffect, useState } from "react";
import MasterPage from "./MasterPage";
import { useAuth } from "../auth";
import { useHistory } from "react-router";
import { Alert, Button, Card, Col, Form, Row } from "react-bootstrap";
import * as api from "../api";
import { FormValidationError } from "../api";
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

  const [formErrorMessage, setErrorMessage] = useState("");
  const [fieldErrors, setFieldErrors] = useState<any>({});

  function handleFormValidationError(error: FormValidationError) {
    if (error.globalErrors.length) {
      setErrorMessage(error.globalErrors.join(". "));
    }

    let newErrors: any = {};
    for (let k in error.fieldErrors) {
      newErrors[k] = error.fieldErrors[k].slice().sort().join(". ");
    }
    setFieldErrors(newErrors);
  }

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formFields = event.currentTarget;
    const fieldData = {
      address: formFields["address"].value,
      birthdate: formFields["birthdate"].value,
      city: formFields["city"].value,
      email: formFields["email"].value,
      firstname: formFields["firstname"].value,
      lastname: formFields["lastname"].value,
      password: formFields["password"].value,
    };

    const error = await api.createUser(fieldData);
    if (error) {
      handleFormValidationError(error);
    } else {
      // The API doesn't return a token on success, so we must log in with a separate request.
      const signInSuccess = await auth.signIn(fieldData.email, fieldData.password);
      if (!signInSuccess) {
        // We assume this never happens
      }
      history.replace("/");
    }
  };

  const errorBanner = formErrorMessage ? (
    <Alert variant="danger" dismissible onClose={() => setErrorMessage("")}>
      {formErrorMessage}
    </Alert>
  ) : null;

  return (
    <MasterPage>
      <h2>Sign Up</h2>
      <Row className="justify-content-center">
        <Col style={{maxWidth: "400px"}}>
          {errorBanner}
          <Card>
            <Card.Body>
              <Form noValidate onSubmit={handleSubmit}>
                <Form.Group controlId="firstname">
                  <Form.Label>First name</Form.Label>
                  <Form.Control
                    required
                    name="First name"
                    type="text"
                    placeholder="Enter First name"
                    isInvalid={!!fieldErrors.firstname}
                  />
                  <Form.Control.Feedback type="invalid">
                    {fieldErrors.firstname}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group controlId="lastname">
                  <Form.Label>Last name</Form.Label>
                  <Form.Control
                    required
                    name="Last name"
                    type="text"
                    placeholder="Enter Last name"
                    isInvalid={!!fieldErrors.lastname}
                  />
                  <Form.Control.Feedback type="invalid">
                    {fieldErrors.lastname}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="birthdate">
                  <Form.Label>Birthdate</Form.Label>
                  <Form.Control
                    required
                    name="Birth date"
                    type="date"
                    isInvalid={!!fieldErrors.birthdate}
                  />
                  <Form.Control.Feedback type="invalid">
                    {fieldErrors.birthdate}
                  </Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="address">
                  <Form.Label>Address</Form.Label>
                  <Form.Control
                    required
                    name="Address"
                    type="text"
                    placeholder="Enter Address"
                    isInvalid={!!fieldErrors.address}
                  />
                  <Form.Control.Feedback type="invalid">
                    {fieldErrors.address}
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group controlId="city">
                  <Form.Label>City</Form.Label>
                  <Form.Control
                    required
                    name="City"
                    type="text"
                    placeholder="Enter City"
                    isInvalid={!!fieldErrors.city}
                  />
                  <Form.Control.Feedback type="invalid">{fieldErrors.city}</Form.Control.Feedback>
                </Form.Group>

                <Form.Group controlId="email">
                  <Form.Label>Email</Form.Label>
                  <Form.Control
                    required
                    name="Email"
                    type="email"
                    placeholder="Enter Email"
                    isInvalid={!!fieldErrors.email}
                  />
                  <Form.Control.Feedback type="invalid">{fieldErrors.email}</Form.Control.Feedback>
                </Form.Group>
                <Form.Group controlId="password">
                  <Form.Label>Password</Form.Label>
                  <Form.Control
                    required
                    name="Password"
                    type="password"
                    placeholder="Enter Password"
                    isInvalid={!!fieldErrors.password}
                  />
                  <Form.Control.Feedback type="invalid">
                    {fieldErrors.password}
                  </Form.Control.Feedback>
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
