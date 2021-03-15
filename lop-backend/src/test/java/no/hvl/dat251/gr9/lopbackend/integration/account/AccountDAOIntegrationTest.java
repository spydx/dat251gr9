package no.hvl.dat251.gr9.lopbackend.integration.account;


import no.hvl.dat251.gr9.lopbackend.entities.Account;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
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

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(IntegrationTestContextConfiguration.class)
class AccountDAOIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccountDAO accountDAO;

    private int numberOfAccounts = 0;

    @BeforeEach
    private void setUp() {
        var emails = new String[]{
                "email1@gmail.no",
                "emial2@hotmail.com",
                "email3@uib.no",
                "eamil4@hvl.no"
        };

        for (var e : emails) {
            var acc = new Account();
            acc.setEmail(e);
            var pwd = e.split("@")[0];
            acc.setPassword(pwd);
            entityManager.persist(acc);
        }
        // +1 is due to injecting admin through SetupService
        numberOfAccounts = emails.length+1;
    }

    @Test
    void persistAccount() {
        var acc = new Account();
        var username = "test@gmail.com";
        var password = "password";
        acc.setEmail(username);
        acc.setPassword(password);

        entityManager.persist(acc);

        var found = accountDAO.findByEmail(username);
        assertThat(found.get().getEmail())
                .isEqualTo(acc.getEmail());
    }

    /*
    admin@lop.no
    email1@gmail.no
    emial2@hotmail.com
    email3@uib.no
    eamil4@hvl.no
    We are injecting one user through
     */
    @Test
    void findAllAccounts() {
        var res = accountDAO.findAll();
        System.out.println("Found accounts");
        for(var a : res ) {
            System.out.println(a.getEmail());
        }
        assertThat(res.size())
                .isEqualTo(numberOfAccounts);
        assertThat(res.size())
                .isNotZero();
    }


}
