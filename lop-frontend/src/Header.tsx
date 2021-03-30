import { Navbar, NavbarBrand, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";

declare module "loplogo.png";

function Header() {
  return (
    <Navbar expand="lg" sticky="top">
      <NavbarBrand>
        <img src="loplogo.png" width="70px" height="70px" alt="" />
      </NavbarBrand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link as={Link} to="/">
            Home
          </Nav.Link>
          <Nav.Link as={Link} to="/signIn">
            Sign In
          </Nav.Link>
          <Nav.Link as={Link} to="/signUp">
            Sign Up
          </Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default Header;
