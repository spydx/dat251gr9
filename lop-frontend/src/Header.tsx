import React from 'react';
import {Navbar, NavbarBrand, Nav} from 'react-bootstrap';
declare module 'loplogo.png'


function Header() {

    return (
        <Navbar expand="lg" sticky="top">
            <NavbarBrand>
                <img src="loplogo.png" width="70px" height="70px" alt="" />
            </NavbarBrand>
            <Navbar.Toggle aria-controls="basic-navbar-nav"/>
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link href="#login">Login</Nav.Link>
                    <Nav.Link href="#register">Register</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
}

export default Header;

/*<Container>
            <Row>
                <Image src="logo.png" ></Image>
            </Row>
            <div>
                
             <span data-testid="text-content" >Dette er logo.png</span>
            </div>
        </Container>*/