import NavBar from "./Header";
import Footer from "./Footer";
import { Fragment } from "react";
import { Container } from "react-bootstrap";

// at the time of writing we assume that every page will have header and footer
const MasterPage = (props: any) => (
  <Fragment>
    <NavBar />
    <Container>{props.children}</Container>
    <Footer />
  </Fragment>
);

export default MasterPage;
