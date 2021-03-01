package no.hvl.dat251.gr9.lopbackend.integration.account;


import no.hvl.dat251.gr9.lopbackend.entities.Account;
import no.hvl.dat251.gr9.lopbackend.entities.dao.AccountDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
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
        numberOfAccounts = emails.length;
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

    @Test
    void findAllAccounts() {
        var res = accountDAO.findAll();
        assertThat(res.size())
                .isEqualTo(numberOfAccounts);
        assertThat(res.size())
                .isNotZero();
    }


}
