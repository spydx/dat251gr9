// todo: should be at the bottom of the page
import { backendRoot } from './services/api';
function Footer() {
  return (
      <div className="text-muted">DAT251 Gruppe9 - { backendRoot }</div>
  );
}

export default Footer;