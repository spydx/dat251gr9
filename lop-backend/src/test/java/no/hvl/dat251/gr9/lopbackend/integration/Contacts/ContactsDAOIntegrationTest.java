package no.hvl.dat251.gr9.lopbackend.integration.Contacts;

import no.hvl.dat251.gr9.lopbackend.entities.Contacts;
import no.hvl.dat251.gr9.lopbackend.entities.dao.ContactsDAO;
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
public class ContactsDAOIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ContactsDAO contactsStorage;

    private int numberOfContacts = 0;

    @BeforeEach
    private void setUp() {
        var emails = new String[]{
                "test1@gmail.no",
                "test2@hotmail.com",
                "test3@uib.no",
                "test4@hvl.no"
        };

        for(var e: emails) {
            var contact = new Contacts();
            contact.setEmail(e);
            var name = e.split("@")[0];
            contact.setName(name);
            contact.setPhone(Integer.toString((int)Math.random()*1000000));
            entityManager.persist(contact);
        }
        // +5 is due to injecting 5 contacts in setupservice
        numberOfContacts = emails.length+5;
    }

    @Test
    void persistContact(){
        var contact = new Contacts();
        var name = "test";
        var email = "test@mail.something";
        var phone = "12345678";

        contact.setName(name);
        contact.setEmail(email);
        contact.setPhone(phone);

        entityManager.persist(contact);

        var found = contactsStorage.findById(contact.getId());
        //this may fail because of Optional being null
        assertEquals(contact.getEmail(), found.get().getEmail());
    }

    @Test
    void findAllAccounts(){
        var res = contactsStorage.findAll();

        assertEquals(numberOfContacts, res.size());
    }
}
