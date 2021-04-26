import { Nav, Navbar } from "react-bootstrap";
import { Link } from "react-router-dom";

import logo from "../assets/loplogo.png";
import { useAuth } from "../auth";

function TopNav() {
  const auth = useAuth();

  return (
    <Navbar bg="light" expand="sm" sticky="top">
      <Navbar.Brand>
        <img src={logo} width="70px" height="70px" alt="" />
      </Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link as={Link} to="/">
            Home
          </Nav.Link>
          <Nav.Link as={Link} to="/events">
            Events
          </Nav.Link>
        </Nav>
        <Nav>
          {auth.isSignedIn() ? (
            <>
              <Nav.Link as={Link} to="/profile">
                Profile
              </Nav.Link>
              <Nav.Link as={Link} to="/signOut">
                Sign Out
              </Nav.Link>
            </>
          ) : (
            <>
              <Nav.Link as={Link} to="/signIn">
                Sign In
              </Nav.Link>
              <Nav.Link as={Link} to="/signUp">
                Sign Up
              </Nav.Link>
            </>
          )}
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default TopNav;
