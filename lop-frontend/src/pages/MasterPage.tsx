import { Container } from "react-bootstrap";
import { BACKEND_ROOT } from "../services/api";
import TopNav from "../components/TopNav";

// at the time of writing we assume that every page will have header and footer
const MasterPage = (props: any) => (
  <div className="d-flex flex-column h-100">
    <TopNav />
    <main className="flex-shrink-0">
      <Container>{props.children}</Container>
    </main>
    <footer className="mt-auto py-3 bg-light">
      <div className="container text-muted">
        <div className="text-muted">DAT251 Gruppe 9</div>
        <div className="text-muted">api: {BACKEND_ROOT}</div>
      </div>
    </footer>
  </div>
);

export default MasterPage;
