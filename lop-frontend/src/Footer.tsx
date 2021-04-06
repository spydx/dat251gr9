// todo: should be at the bottom of the page
import { Container } from 'react-bootstrap';
import { backendRoot } from './services/api';
function Footer() {
  return (
    <Container>
      <div className="text-muted">DAT251 Gruppe9</div>
      <div className="text-muted">{ backendRoot }</div>
    </Container>
  );
}

export default Footer;