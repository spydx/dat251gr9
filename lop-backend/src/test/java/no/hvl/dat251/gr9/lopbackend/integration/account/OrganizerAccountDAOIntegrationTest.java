package no.hvl.dat251.gr9.lopbackend.integration.account;

import no.hvl.dat251.gr9.lopbackend.entities.OrganizerAccount;
import no.hvl.dat251.gr9.lopbackend.entities.UserAccount;
import no.hvl.dat251.gr9.lopbackend.entities.dao.OrganizerAccountDAO;
import no.hvl.dat251.gr9.lopbackend.integration.IntegrationTestContextConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
public class OrganizerAccountDAOIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrganizerAccountDAO organizerAccountDAO;

    private int numberOfAccounts = 0;

    @BeforeEach
    private void setUp(){
        var emails = new String[]{
                "email1@gmail.no",
                "emial2@hotmail.com",
                "email3@uib.no",
                "eamil4@hvl.no"
        };

        for (var e : emails) {
            var acc = new OrganizerAccount();
            acc.setEmail(e);
            var pwd = e.split("@")[0];
            acc.setPassword(pwd);
            entityManager.persist(acc);
        }
        // +1 is due to injecting organizer through SetupService
        numberOfAccounts = emails.length+1;
    }

    @Test
    void persistOrganizerAccount() {
        var acc = new OrganizerAccount();
        var username = "test@gmail.com";
        var password = "password";
        acc.setEmail(username);
        acc.setPassword(password);

        entityManager.persist(acc);

        var found = organizerAccountDAO.findByEmail(username);
        assertEquals(acc.getEmail(), found.get().getEmail());
    }

    @Test
    void findAllOrganizerAccounts(){
        var res = organizerAccountDAO.findAll();

        assertEquals(numberOfAccounts, res.size());
        assertThat(res.size()).isNotZero();
    }
}
