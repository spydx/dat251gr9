import { Button, Card, Form, Row } from "react-bootstrap";
import MasterPage from "../MasterPage";
import { ApiPath, doPost } from "../services/api";

/** sends the login request and saves the token */
async function performLogin(email: string, password: string) {
  const { token } = await doPost(ApiPath.Login, {
    email: email,
    password: password,
  });
  localStorage.setItem("token", token);
}

// TODO: form validation / errors
export const SignInPage: React.FunctionComponent = () => {
  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const formData = event.currentTarget;
    await performLogin(formData["formEmail"].value, formData["formPassword"].value);
    alert("signed in with token " + localStorage.getItem("token"));
  };

  return (
    <MasterPage>
      <h2>Sign In</h2>
      <Row className="justify-content-center">
        <Card>
          <Card.Body>
            <Form noValidate={false} className="signInForm" onSubmit={handleSubmit}>
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
      </Row>
    </MasterPage>
  );
};
